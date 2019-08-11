import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class Populate {


    static Connection oracle_connect()
    {
        String oracle_url = "jdbc:oracle:thin:@localhost:1521:orclcdb";
        String oracle_username = "system";
        String oracle_password = "oracle";
        try
        {
            Connection conn = DriverManager.getConnection(oracle_url, oracle_username, oracle_password);
            return conn;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void execute_sql(Connection conn, String statement) throws Exception
    {
        Statement sql_statement = conn.createStatement();
        try
        {
            sql_statement.executeUpdate(statement);
        }
        catch (SQLIntegrityConstraintViolationException e) { }

        sql_statement.close();
    }

    public static void read_file(String arg, String db_table, int[] cols, Connection conn) throws Exception
    {
        try
        {
            Scanner read = new Scanner(new File(arg), "ISO-8859-1");

            if (db_table.equals("movies") || db_table.equals("tags"))
            {
                System.out.println(String.format("Running SQL DELETE (and Cascading) for %s", db_table));

                String sql_delete_query = String.format("DELETE FROM %s", db_table);
                execute_sql(conn, sql_delete_query);
            }
                

            System.out.println(String.format("Running SQL INSERT for %s", db_table));

            read.nextLine();

            while (read.hasNextLine())
            {
                String[] row = new String[cols.length];
                String[] line_data = read.nextLine().split("\t");

                try
                {
                    for (int i = 0; i < cols.length; i++)
                    {
                        row[i] = line_data[cols[i]].replace("'", "").replace("\\N","0");
                    }
                    String sql_insert = String.format("INSERT INTO %s VALUES ('%s')", db_table, String.join("\',\'", row));
                    execute_sql(conn, sql_insert);
                }
                catch(ArrayIndexOutOfBoundsException ex)
                {
                    continue;
                }
            }
            read.close();
            System.out.println(String.format("Completed SQL INSERT for %s", db_table));     
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found");
        }
    }

    public static void process(String arg, Connection conn) throws Exception
    {
        int[] movies = new int[] {0, 1, 5, 7, 8, 12, 13, 17, 18};
        int[] movie_genres = new int[] {0, 1};
        int[] movie_countries = new int[] {0, 1};
        int[] movie_locations = new int[] {0, 1};
        int[] tags = new int[] {0, 1};
        int[] movie_tags = new int[] {0, 1, 2};
        int[] movie_actors = new int[] {0, 1, 2, 3};
        int[] movie_directors = new int[] {0, 1, 2};
        int[] user_ratedmovies = new int[] {0, 1, 2};
        int[] user_taggedmovies = new int[] {0, 1, 2};

        String db_table = arg.replace(".dat", "").replace("dataset/", "");
        System.out.println(db_table);
        
        if (db_table.equals("movies"))
        {
            read_file(arg, db_table, movies, conn);
        }
        else if (db_table.equals("movie_genres"))
        {
            read_file(arg, db_table, movie_genres, conn);
        }
        else if (db_table.equals("movie_countries"))
        {
            read_file(arg, db_table, movie_countries, conn);
        }
        else if (db_table.equals("movie_locations"))
        {
            read_file(arg, db_table, movie_locations, conn);
        }
        else if (db_table.equals("tags"))
        {
            read_file(arg, db_table, tags, conn);
        }
        else if (db_table.equals("movie_tags"))
        {
            read_file(arg, db_table, movie_tags, conn);
        }
        else if (db_table.equals("movie_actors"))
        {
            read_file(arg, db_table, movie_actors, conn);
        }
        else if (db_table.equals("movie_directors"))
        {
            read_file(arg, db_table, movie_directors, conn);
        }
        else if (db_table.equals("user_ratedmovies"))
        {
            read_file(arg, db_table, user_ratedmovies, conn);
        }
        else if (db_table.equals("user_taggedmovies"))
        {
            read_file(arg, db_table, user_taggedmovies, conn);
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        // Load Oracle driver
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println(cnfe);
        }

        Connection conn = oracle_connect();
        conn.setAutoCommit(true);

        if (args.length > 0)
        {
            for (int i = 0; i < args.length; i++)
            {
                process(args[i], conn);        
            }
        }
        else
        {
            System.out.println("No args provided");
        }

        conn.close();
    }

}
