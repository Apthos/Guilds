package net.apthos.guilds.database;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.player.Profile;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PDUtil {

    public static Boolean hasPlayer(Player player){
        try {
            Connection con = Guilds.getGuildsDatabase().getConnection();
            PreparedStatement statement = con.prepareStatement("" +
                    "SELECT * FROM Guilds.Players WHERE uuid = ?;"
            );
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            con.close();
            statement.close();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addPlayer(Player player){
        try {
            Connection con = Guilds.getGuildsDatabase().getConnection();
            PreparedStatement statement = con.prepareStatement("" +
                    "INSERT INTO Guilds.Players VALUES (?, ?, ?);"
            );
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, player.getName());
            Profile profile = Guilds.getInstance().getProfile(player);
            if (profile.hasGuild()){
                statement.setString(3, profile.getGuild().getGUID());
            } else statement.setString(3, "$NULL$");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
