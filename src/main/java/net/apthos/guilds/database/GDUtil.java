package net.apthos.guilds.database;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.guild.Guild;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GDUtil {

    public static Boolean hasGuild(String identifier){
        Guild guild = Guilds.getInstance().getGuild(identifier);
        if (guild == null){
            try {
                Connection con = Guilds.getGuildsDatabase().getConnection();
                if (identifier.contains("-")){
                    return con.prepareStatement("SELECT * FROM Guilds.Guilds WHERE guid=?"
                    ).getResultSet().next();
                } else {
                    return con.prepareStatement
                            ("SELECT * FROM Guilds.Guilds WHERE guildname=?").getResultSet()
                            .next();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            return true;
        }
        return null;
    }

    public static void addGuild(Guild guild){
        if (hasGuild(guild.getGUID())) return;
        try {
            PreparedStatement statement =
                    Guilds.getGuildsDatabase().getConnection().prepareStatement("" +
                            "INSERT INTO Guilds.Guilds VALUES (?, ?, ?, ?);"
                    );
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
