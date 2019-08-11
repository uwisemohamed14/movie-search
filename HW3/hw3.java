import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import java.sql.*;

public class hw3 extends JFrame {

    // Logic Structures
    Connection conn;
    Reloader reload_obj = new Reloader();
    String andor_sql_type = "INTERSECT";

    Color blue = new Color(110, 180, 250);
    Color white = new Color(225, 225, 225);

    // GUI Components
    Dimension labelDimension=new Dimension(0, 40);

    JPanel top_panel;
    JLabel top_label;

    JPanel main_panel;
    JLabel main_label;

    JPanel genre_panel;
    JPanel genre_display;
    JScrollPane genre_scroll;
    JLabel genre_label;
    
    JPanel country_panel;
    JPanel country_display;
    JScrollPane country_scroll;
    JLabel country_label;

    JPanel location_panel;
    JPanel location_display;
    JScrollPane location_scroll;
    JLabel location_label;

    JPanel andor_panel;
    JLabel andor_label;
    JComboBox<String> andor_combobox;

    JPanel top_left_panel;
    JPanel top_left_andor_panel;

    JPanel movie_year_panel;
    JPanel movie_year_options_panel;
    JLabel movie_year_label;
    JLabel from_label;
    JLabel to_label;
    JComboBox<String> from_combobox;
    JComboBox<String> to_combobox;

    JPanel critics_rating_panel;
    JPanel critics_rating_options_panel;
    JLabel critics_rating_label;
    JLabel rating_label;
    JLabel rating_value_label;
    JLabel review_count_label;
    JLabel review_count_value_label;
    JComboBox<String> rating_combobox;
    JComboBox<String> review_count_combobox;
    JTextField rating_value_textbox;
    JTextField review_count_value_textbox;

    JPanel movie_tags_panel;
    JPanel movie_tags_display;
    JScrollPane movie_tags_scroll;
    JLabel movie_tags_label;

    JPanel top_right_panel;

    JPanel tag_weight_panel;
    JLabel tag_weight_label;
    JLabel tag_weight_value_label;
    JComboBox<String> tag_weight_combobox;
    JTextField tag_weight_value_textbox;

    JTextArea query_area;
    JPanel query_panel;
    JPanel query_panel2;
    JPanel bottom_panel;
    JButton query_button;

    JLabel result_label;
    JPanel result_panel;
    JTextArea result_area;
    JPanel result_display;
    JScrollPane result_scroll;


    private void load_interface() 
    {

        genre_panel = new JPanel();
        genre_display = new JPanel();
        genre_scroll = new JScrollPane();
        genre_label = set_label(genre_label, "Genres", true);
        genre_panel.setLayout(new BorderLayout());
        genre_panel.add(genre_label, BorderLayout.NORTH);
        genre_panel.add(genre_scroll, BorderLayout.CENTER);
        genre_scroll.setViewportView(genre_display);
        genre_scroll.getVerticalScrollBar().setUnitIncrement(16);

        country_panel = new JPanel();
        country_display = new JPanel();
        country_scroll = new JScrollPane();
        country_label = set_label(country_label, "Country", true);
        country_panel.setLayout(new BorderLayout());
        country_panel.add(country_label, BorderLayout.NORTH);
        country_panel.add(country_scroll, BorderLayout.CENTER);
        country_scroll.setViewportView(country_display);
        country_display.setLayout(new GridLayout(0,1));
        country_scroll.getVerticalScrollBar().setUnitIncrement(16);
        country_scroll.getHorizontalScrollBar().setUnitIncrement(16);

        location_panel = new JPanel();
        location_display = new JPanel();
        location_scroll = new JScrollPane();
        location_label = set_label(location_label, "Filming Location Country", true);
        location_panel.setLayout(new BorderLayout());
        location_panel.add(location_label, BorderLayout.NORTH);
        location_panel.add(location_scroll, BorderLayout.CENTER);
        location_scroll.setViewportView(location_display);
        location_display.setLayout(new GridLayout(0,1));
        location_scroll.getVerticalScrollBar().setUnitIncrement(16);
        location_scroll.getHorizontalScrollBar().setUnitIncrement(16);
        
        top_left_panel = new JPanel();
        top_left_panel.setLayout(new GridLayout(1,3));
        top_left_panel.add(genre_panel);
        top_left_panel.add(country_panel);
        top_left_panel.add(location_panel);

        andor_panel = new JPanel();
        andor_label = new JLabel("Search Between Attributes' Values:", SwingConstants.CENTER);
        andor_label.setForeground(Color.darkGray);
        andor_combobox = new JComboBox<String>();
        andor_panel.setLayout(new GridLayout(1,2));
        andor_panel.add(andor_label);
        andor_panel.add(andor_combobox);
        andor_panel.setBackground(blue);
        andor_combobox.addItem("AND");
        andor_combobox.addItem("OR");
        andor_combobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_andor();
            }
        });

        top_panel = new JPanel();
        top_panel.setLayout(new BorderLayout());
        top_panel.add(top_left_panel, BorderLayout.CENTER);
        top_panel.add(andor_panel, BorderLayout.SOUTH);
        top_panel.setOpaque(true);
        set_height(top_panel, 400);

        ///////////////////////////////////
        //         Second quadrant       //
        ///////////////////////////////////

        from_label = new JLabel("from", SwingConstants.CENTER);
        to_label = new JLabel("to", SwingConstants.CENTER);

        from_combobox = new JComboBox<String>();
        from_combobox.addItem("Select");
        for (int i = 1900; i <= 2019; i++) {
            from_combobox.addItem(Integer.toString(i));
        }
        from_combobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_movie_year();
            }
        });

        to_combobox = new JComboBox<String>();
        to_combobox.addItem("Select");
        for (int i = 1900; i <= 2019; i++) {
            to_combobox.addItem(Integer.toString(i));
        }
        to_combobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_movie_year();
            }
        });

        movie_year_options_panel = new JPanel();
        movie_year_options_panel.setLayout(new GridLayout(2,2));
        movie_year_options_panel.add(from_label);
        movie_year_options_panel.add(from_combobox);
        movie_year_options_panel.add(to_label);
        movie_year_options_panel.add(to_combobox);
        movie_year_options_panel.setBackground(white);

        movie_year_panel = new JPanel();
        movie_year_label = set_label(movie_year_label, "Movie Year", true);
        movie_year_panel.setLayout(new BorderLayout());
        movie_year_panel.add(movie_year_label, BorderLayout.NORTH);
        movie_year_panel.add(movie_year_options_panel, BorderLayout.CENTER);
        movie_year_panel.setOpaque(true);

        // Critics Rating


        rating_label = new JLabel("Rating:", SwingConstants.CENTER);
        rating_value_label = new JLabel("Value:", SwingConstants.CENTER);
        review_count_label = new JLabel("Num of Reviews:", SwingConstants.CENTER);
        review_count_value_label = new JLabel("Value:", SwingConstants.CENTER);

        rating_combobox = new JComboBox<String>();
        rating_combobox.addItem("Select");
        rating_combobox.addItem("=");
        rating_combobox.addItem("<");
        rating_combobox.addItem(">");
        rating_combobox.addItem("<=");
        rating_combobox.addItem(">=");
        rating_combobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_rating();
            }
        });

        review_count_combobox = new JComboBox<String>();
        review_count_combobox.addItem("Select");
        review_count_combobox.addItem("=");
        review_count_combobox.addItem("<");
        review_count_combobox.addItem(">");
        review_count_combobox.addItem("<=");
        review_count_combobox.addItem(">=");
        review_count_combobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_review_count();
            }
        });

        rating_value_textbox = new JTextField(10);
        rating_value_textbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_rating();
            }
        });

        review_count_value_textbox = new JTextField(10);
        review_count_value_textbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_review_count();
            }
        });

        critics_rating_options_panel = new JPanel();
        critics_rating_options_panel.setLayout(new GridLayout(4,2));
        critics_rating_options_panel.add(rating_label);
        critics_rating_options_panel.add(rating_combobox);
        critics_rating_options_panel.add(rating_value_label);
        critics_rating_options_panel.add(rating_value_textbox);
        critics_rating_options_panel.add(review_count_label);
        critics_rating_options_panel.add(review_count_combobox);
        critics_rating_options_panel.add(review_count_value_label);
        critics_rating_options_panel.add(review_count_value_textbox);

        critics_rating_panel = new JPanel();
        critics_rating_label = set_label(critics_rating_label, "Critics' Rating", true);
        critics_rating_panel.setLayout(new BorderLayout());
        critics_rating_panel.add(critics_rating_label, BorderLayout.NORTH);
        critics_rating_panel.add(critics_rating_options_panel, BorderLayout.CENTER);
        critics_rating_panel.add(movie_year_panel, BorderLayout.SOUTH);
        critics_rating_panel.setOpaque(true);

        // Movie Tag Values

        tag_weight_label = new JLabel("Tag Weight:", SwingConstants.CENTER);
        tag_weight_value_label = new JLabel("Value:", SwingConstants.CENTER);

        tag_weight_combobox = new JComboBox<String>();
        tag_weight_combobox.addItem("Select");
        tag_weight_combobox.addItem("=");
        tag_weight_combobox.addItem("<");
        tag_weight_combobox.addItem(">");
        tag_weight_combobox.addItem("<=");
        tag_weight_combobox.addItem(">=");
        tag_weight_combobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_tag_weight();
            }
        });

        tag_weight_value_textbox = new JTextField(10);
        tag_weight_value_textbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.update_tag_weight();
            }
        });

        tag_weight_panel = new JPanel();
        tag_weight_panel.setLayout(new GridLayout(2,2));
        tag_weight_panel.add(tag_weight_label);
        tag_weight_panel.add(tag_weight_combobox);
        tag_weight_panel.add(tag_weight_value_label);
        tag_weight_panel.add(tag_weight_value_textbox);


        movie_tags_panel = new JPanel();
        movie_tags_display = new JPanel();
        movie_tags_scroll = new JScrollPane();
        movie_tags_label = set_label(movie_tags_label, "Movie Tag Values", true);
        movie_tags_panel.setLayout(new BorderLayout());
        movie_tags_panel.add(movie_tags_label, BorderLayout.NORTH);
        movie_tags_panel.add(movie_tags_scroll, BorderLayout.CENTER);
        movie_tags_panel.add(tag_weight_panel, BorderLayout.SOUTH);
        movie_tags_scroll.setViewportView(movie_tags_display);
        movie_tags_display.setLayout(new BorderLayout());
        movie_tags_scroll.getVerticalScrollBar().setUnitIncrement(16);
        movie_tags_scroll.getHorizontalScrollBar().setUnitIncrement(16);

        top_right_panel = new JPanel();
        top_right_panel.setLayout(new GridLayout(1,2));
        top_right_panel.add(critics_rating_panel);
        top_right_panel.add(movie_tags_panel);
        top_right_panel.setOpaque(true);

        // Bottom panel

        query_area = new JTextArea(5, 10);
        JScrollPane query_scroll = new JScrollPane(query_area); 
        query_area.setLineWrap(true);
        query_area.setEditable(false);

        query_button = new JButton("Execute Query");
        query_button.setBackground(blue);
        query_button.setOpaque(true);
        query_button.setBorderPainted(false);
        query_button.setPreferredSize(new Dimension(0, 40));
        query_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reload_obj.execute_query();
            }
        });

        query_panel = new JPanel();
        query_panel.setLayout(new BorderLayout());
        query_panel.add(query_area, BorderLayout.CENTER);
        query_panel.add(query_button, BorderLayout.SOUTH);

        result_scroll = new JScrollPane();
        result_label = set_label(result_label, "Result", true);
        result_panel = new JPanel();
        result_display = new JPanel();
        result_panel.add(result_scroll, BorderLayout.CENTER);
        result_scroll.setViewportView(result_display);
        result_scroll.getVerticalScrollBar().setUnitIncrement(16);
        result_scroll.getHorizontalScrollBar().setUnitIncrement(16);
        result_display.setLayout(new BorderLayout());
        result_panel.setLayout(new BorderLayout());
        result_panel.add(result_label, BorderLayout.NORTH);
        result_panel.add(result_scroll, BorderLayout.CENTER);

        bottom_panel = new JPanel();
        bottom_panel.setLayout(new GridLayout(1,2));
        bottom_panel.add(query_panel);
        bottom_panel.add(result_panel);
        bottom_panel.setOpaque(true);

        // Final

        main_panel = new JPanel();
        main_label = set_label(main_label, "Movie", false);
        main_panel.setLayout(new GridLayout(1,2));
        main_panel.add(top_panel);
        main_panel.add(top_right_panel);
        main_panel.setOpaque(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));
        add(main_panel);
        add(bottom_panel);
        setSize(1200, 800);
        setVisible(true);
    }

    // Logic Components
    List<JCheckBox> list_of_genres = new ArrayList<>();
    List<JCheckBox> list_of_countries = new ArrayList<>();
    List<JCheckBox> list_of_locations = new ArrayList<>();
    List<JCheckBox> list_of_movie_tags = new ArrayList<>();

    private void load_genres()
    {
        try
        {
            genre_display.setLayout(new GridLayout(0,1));
            String sql_query = "select distinct(genre) from movie_genres order by genre";
            Statement sql_statement = conn.createStatement();
            ResultSet sql_results = sql_statement.executeQuery(sql_query);
            
            while(sql_results.next())
            {
                JCheckBox genre_option = new JCheckBox();
                genre_option.setText(sql_results.getString(1));
                genre_display.add(genre_option);
                
                genre_option.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        if (genre_option.isSelected())
                        {
                            list_of_genres.add(genre_option);
                        }
                        else
                        {
                            list_of_genres.remove(genre_option);
                        }
                        
                        reload_obj.clear_country();
                        reload_obj.update_country();
                        reload_obj.clear_location();
                        reload_obj.update_location();
                        reload_obj.clear_movie_tags();
                        reload_obj.update_movie_tags();
                    }
                });
            }
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private class Reloader
    {

        private void clear_country()
        {
            country_display.removeAll();
            list_of_countries.clear();
        }

        private void clear_location()
        {
            location_display.removeAll();
            list_of_locations.clear();
        }

        private void clear_movie_tags()
        {
            movie_tags_display.removeAll();
            list_of_movie_tags.clear();
        }

        private void update_country()
        {
            try
            {

                String sql_query = String.format("select distinct(mc.country) from movie_countries mc where mc.mid in (%s) order by mc.country", get_query_genres(list_of_genres));
                // System.out.println(sql_query);

                Statement sql_statement = conn.createStatement();
                ResultSet sql_results = sql_statement.executeQuery(sql_query);

                while (sql_results.next())
                {
                    JCheckBox country_option = new JCheckBox();
                    country_option.setText(sql_results.getString(1));
                    country_display.add(country_option);
                    
                    country_option.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                            if (country_option.isSelected())
                            {
                                list_of_countries.add(country_option);
                            }
                            else
                            {
                                list_of_countries.remove(country_option);
                            }
                            
                            reload_obj.clear_location();
                            reload_obj.update_location();
                            reload_obj.clear_movie_tags();
                            reload_obj.update_movie_tags();
                        }
                    });
                }
                country_display.updateUI();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private void update_location()
        {
            try
            {
                String sql_genre = get_query_genres(list_of_genres);
                String sql_country = get_query_countries(list_of_countries);
                String sql_query;

                if (sql_genre.equals("NULL"))
                {
                    location_display.updateUI();
                    return;
                }

                List<String> sql_list = new ArrayList<String>();

                sql_list.add(sql_genre);

                if (!sql_country.equals("NULL"))
                {
                    sql_list.add(sql_country);
                }

                String sql_query_partial = String.join(") INTERSECT (", sql_list);

                sql_query = String.format("select distinct(ml.country) from movie_locations ml where ml.mid in ((%s)) order by ml.country", sql_query_partial);
            
                // System.out.println(sql_query);

                Statement sql_statement = conn.createStatement();
                ResultSet sql_results = sql_statement.executeQuery(sql_query);

                while (sql_results.next())
                {
                    JCheckBox location_option = new JCheckBox();
                    location_option.setText(sql_results.getString(1));
                    location_display.add(location_option);
                    
                    location_option.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                            if (location_option.isSelected())
                            {
                                list_of_locations.add(location_option);
                            }
                            else
                            {
                                list_of_locations.remove(location_option);
                            }

                            reload_obj.clear_movie_tags();
                            reload_obj.update_movie_tags();
                        }
                    });
                }
                location_display.updateUI();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private void update_andor()
        {
            
            if (andor_combobox.getSelectedItem() != null)
            {
                
                String andor_value = andor_combobox.getSelectedItem().toString();

                if (andor_value.equals("AND"))
                {
                    andor_sql_type = "INTERSECT";
                }
                else
                {
                    andor_sql_type = "UNION";
                }

                reload_obj.clear_country();
                reload_obj.update_country();
                reload_obj.clear_location();
                reload_obj.update_location();
                reload_obj.clear_movie_tags();
                reload_obj.update_movie_tags();
            }
        }

        private void update_movie_tags()
        {
            try
            {
                String sql_genre = get_query_genres(list_of_genres);
                String sql_country = get_query_countries(list_of_countries);
                String sql_location = get_query_locations(list_of_locations);
                String sql_rating = get_query_rating();
                String sql_review_count = get_query_review_count();
                String sql_movie_year = get_query_movie_year();
                String sql_tag_weight = get_query_tag_weight();
                String sql_query;

                if (sql_genre.equals("NULL"))
                {
                    movie_tags_display.updateUI();
                    return;
                }

                List<String> sql_list = new ArrayList<String>();

                sql_list.add(sql_genre);

                if (!sql_country.equals("NULL"))
                {
                    sql_list.add(sql_country);
                }

                if (!sql_location.equals("NULL"))
                {
                    sql_list.add(sql_location);
                }

                if (!sql_rating.equals("NULL"))
                {
                    sql_list.add(sql_rating);
                }

                if (!sql_review_count.equals("NULL"))
                {
                    sql_list.add(sql_review_count);
                }

                if (!sql_movie_year.equals("NULL"))
                {
                    sql_list.add(sql_movie_year);
                }

                String sql_query_partial = String.join(") INTERSECT (", sql_list);

                if (!sql_tag_weight.equals("NULL"))
                {
                    sql_query = String.format("select t.tagvalue from tags t where t.tid in (select distinct(mt.tid) from movie_tags mt where mt.mid in ((%s)) INTERSECT %s and mt2.mid in ((%s))) order by t.tagvalue", sql_query_partial, sql_tag_weight, sql_query_partial);
                }
                else
                {
                    sql_query = String.format("select t.tagvalue from tags t where t.tid in (select distinct(mt.tid) from movie_tags mt where mt.mid in ((%s))) order by t.tagvalue", sql_query_partial);
                }
            
                // System.out.println(sql_query);

                Statement sql_statement = conn.createStatement();
                ResultSet sql_results = sql_statement.executeQuery(sql_query);
                Integer count = 0;
                JPanel movie_tags_subdisplay = new JPanel();
                movie_tags_subdisplay.setLayout(new GridLayout(0,1));

                while (sql_results.next())
                {
                    count += 1;
                    JCheckBox movie_tags_option = new JCheckBox();
                    movie_tags_option.setText(sql_results.getString(1));
                    movie_tags_subdisplay.add(movie_tags_option);
                    
                    movie_tags_option.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                            if (movie_tags_option.isSelected())
                            {
                                list_of_movie_tags.add(movie_tags_option);
                            }
                            else
                            {
                                list_of_movie_tags.remove(movie_tags_option);
                            }
                        }
                    });
                }

                JLabel count_label = new JLabel();
                count_label.setPreferredSize(new Dimension(0, 30));
                count_label.setText("Tags count: " + count.toString());

                movie_tags_display.add(count_label, BorderLayout.NORTH);
                movie_tags_display.add(movie_tags_subdisplay, BorderLayout.CENTER);
                movie_tags_display.updateUI();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private void update_rating()
        {
            reload_obj.clear_movie_tags();
            reload_obj.update_movie_tags();
        }

        private void update_review_count()
        {
            reload_obj.clear_movie_tags();
            reload_obj.update_movie_tags();
        }

        private void update_movie_year()
        {
            reload_obj.clear_movie_tags();
            reload_obj.update_movie_tags();
        }

        private void update_tag_weight()
        {
            reload_obj.clear_movie_tags();
            reload_obj.update_movie_tags();
        }

        private void execute_query()
        {
            try
            {
                String sql_genre = get_query_genres(list_of_genres);
                String sql_country = get_query_countries(list_of_countries);
                String sql_location = get_query_locations(list_of_locations);
                String sql_rating = get_query_rating();
                String sql_review_count = get_query_review_count();
                String sql_movie_year = get_query_movie_year();
                // String sql_tag_weight_mid = get_query_tag_weight_mid();
                String sql_movie_tags = get_query_movie_tags(list_of_movie_tags);
                String sql_query;

                if (sql_genre.equals("NULL"))
                {
                    movie_tags_display.updateUI();
                    return;
                }

                List<String> sql_list = new ArrayList<String>();

                sql_list.add(sql_genre);

                if (!sql_country.equals("NULL"))
                {
                    sql_list.add(sql_country);
                }

                if (!sql_location.equals("NULL"))
                {
                    sql_list.add(sql_location);
                }

                if (!sql_rating.equals("NULL"))
                {
                    sql_list.add(sql_rating);
                }

                if (!sql_review_count.equals("NULL"))
                {
                    sql_list.add(sql_review_count);
                }

                if (!sql_movie_year.equals("NULL"))
                {
                    sql_list.add(sql_movie_year);
                }

                if (!sql_movie_tags.equals("NULL"))
                {
                    sql_list.add(sql_movie_tags);
                }

                // if (!sql_tag_weight_mid.equals("NULL"))
                // {
                //     sql_list.add(sql_tag_weight_mid);
                // }

                String sql_query_partial = String.join(") INTERSECT (", sql_list);

                sql_query = String.format("select mf.title, mf.year, (select mcf.country from movie_countries mcf where mcf.mid = mf.mid), round((mf.all_critics_rating+mf.top_critics_rating+mf.audience_rating+mf.audience_rating)/3, 2), round((mf.all_critics_num+mf.top_critics_num+mf.audience_num)/3), (select listagg(mlf.country, ', ') WITHIN GROUP (ORDER BY mlf.country) from movie_locations mlf where mlf.mid=mf.mid), (select listagg(mgf.genre, ', ') WITHIN GROUP (ORDER BY mgf.genre) from movie_genres mgf where mgf.mid=mf.mid) from movies mf where mf.mid in ((%s)) order by mf.title", sql_query_partial);
            
                System.out.println(sql_query);
                query_area.setText(sql_query);

                Statement sql_statement = conn.createStatement();
                ResultSet sql_results = sql_statement.executeQuery(sql_query);

                Integer count = 0;
                List<String> ans = new ArrayList<>();

                while (sql_results.next())
                {
                    count += 1;
                    String temp = String.format("%s (%s - %s) - (Rating, Count): (%s, %s) - (Locations: %s) - (Genre: %s)", sql_results.getString(1), sql_results.getString(2), sql_results.getString(3), sql_results.getString(4), sql_results.getString(5), sql_results.getString(6), sql_results.getString(7));
                    ans.add(temp);
                }

                JLabel count_label = new JLabel();
                count_label.setPreferredSize(new Dimension(0, 40));
                count_label.setText("Movies count: " + count.toString());

                result_display.removeAll();

                String[] results_list = ans.toArray(new String[0]);
                JList<String> results = new JList<String>(results_list);

                result_display.add(count_label, BorderLayout.NORTH);
                result_display.add(results, BorderLayout.CENTER);
                result_display.updateUI();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private String get_query_genres(List<JCheckBox> list_of_genres)
    {

        List<String> mid_from_genres_querylist = new ArrayList<>();

        for(JCheckBox genre_option:list_of_genres)
        {
            mid_from_genres_querylist.add(String.format("select mg.mid from movie_genres mg where mg.genre = '%s'", genre_option.getText()));
        }

        String mid_from_genres_query;

        if (mid_from_genres_querylist.isEmpty())
        {
            mid_from_genres_query = "NULL";
        }
        else
        {
            mid_from_genres_query = String.join(" " + andor_sql_type + " ", mid_from_genres_querylist);
        }

        return mid_from_genres_query;
    }

    private String get_query_countries(List<JCheckBox> list_of_countries)
    {

        List<String> mid_from_countries_querylist = new ArrayList<>();

        for(JCheckBox country_option:list_of_countries)
        {
            mid_from_countries_querylist.add(String.format("select mc.mid from movie_countries mc where mc.country = '%s'", country_option.getText()));
        }

        String mid_from_countries_query;

        if (mid_from_countries_querylist.isEmpty())
        {
            mid_from_countries_query = "NULL";
        }
        else
        {
            mid_from_countries_query = String.join(" " + andor_sql_type + " ", mid_from_countries_querylist);
        }

        return mid_from_countries_query;
    }

    private String get_query_locations(List<JCheckBox> list_of_locations)
    {

        List<String> mid_from_locations_querylist = new ArrayList<>();

        for(JCheckBox location_option:list_of_locations)
        {
            mid_from_locations_querylist.add(String.format("select ml.mid from movie_locations ml where ml.country = '%s'", location_option.getText()));
        }

        String mid_from_locations_query;

        if (mid_from_locations_querylist.isEmpty())
        {
            mid_from_locations_query = "NULL";
        }
        else
        {
            mid_from_locations_query = String.join(" " + andor_sql_type + " ", mid_from_locations_querylist);
        }

        return mid_from_locations_query;
    }

    private String get_query_rating()
    {

        String rating_type = rating_combobox.getSelectedItem().toString();

        if (rating_type.equals("Select"))
        {
            return "NULL";
        }

        String rating_value = rating_value_textbox.getText();
        
        if (rating_value.length() == 0)
        {
            return "NULL";
        }

        String mid_from_rating_query;

        mid_from_rating_query = String.format("select m.mid from movies m where (m.all_critics_rating+m.top_critics_rating+m.audience_rating+m.audience_rating)/3 %s %s", rating_type, rating_value);

        return mid_from_rating_query;
    }

    private String get_query_review_count()
    {

        String review_count_type = review_count_combobox.getSelectedItem().toString();

        if (review_count_type.equals("Select"))
        {
            return "NULL";
        }

        String review_count_value = review_count_value_textbox.getText();
        
        if (review_count_value.length() == 0)
        {
            return "NULL";
        }

        String mid_from_review_count_query;

        mid_from_review_count_query = String.format("select m.mid from movies m where (m.all_critics_num+m.top_critics_num +m.audience_num)/3 %s %s", review_count_type, review_count_value);

        return mid_from_review_count_query;
    }

    private String get_query_movie_year()
    {

        String from_value = from_combobox.getSelectedItem().toString();
        String to_value = to_combobox.getSelectedItem().toString();

        if (from_value.equals("Select") && to_value.equals("Select"))
        {
            return "NULL";
        }

        String mid_from_movie_year_query;

        if (to_value.equals("Select"))
        {
            mid_from_movie_year_query = String.format("select m.mid from movies m where m.year >= %s", from_value);
        }
        else if (from_value.equals("Select"))
        {
            mid_from_movie_year_query = String.format("select m.mid from movies m where m.year <= %s", to_value);
        }
        else
        {
            mid_from_movie_year_query = String.format("select m.mid from movies m where m.year >= %s and m.year <= %s", from_value, to_value);
        }

        return mid_from_movie_year_query;
    }

    private String get_query_tag_weight()
    {

        String tag_weight_type = tag_weight_combobox.getSelectedItem().toString();

        if (tag_weight_type.equals("Select"))
        {
            return "NULL";
        }

        String tag_weight_value = tag_weight_value_textbox.getText();
        
        if (tag_weight_value.length() == 0)
        {
            return "NULL";
        }

        String mid_from_tag_weight_query;

        mid_from_tag_weight_query = String.format("select mt2.tid from movie_tags mt2 where mt2.tagweight %s %s", tag_weight_type, tag_weight_value);

        return mid_from_tag_weight_query;
    }

    private String get_query_tag_weight_mid()
    {

        String tag_weight_type = tag_weight_combobox.getSelectedItem().toString();

        if (tag_weight_type.equals("Select"))
        {
            return "NULL";
        }

        String tag_weight_value = tag_weight_value_textbox.getText();
        
        if (tag_weight_value.length() == 0)
        {
            return "NULL";
        }

        String mid_from_tag_weight_query;

        mid_from_tag_weight_query = String.format("select mt2.mid from movie_tags mt2, tags t where mt2.tagweight %s %s and t.tid = mt2.tid", tag_weight_type, tag_weight_value);

        return mid_from_tag_weight_query;
    }

    private String get_query_movie_tags(List<JCheckBox> list_of_movie_tags)
    {

        List<String> mid_from_movie_tags_querylist = new ArrayList<>();

        for(JCheckBox movie_tags_option:list_of_movie_tags)
        {
            mid_from_movie_tags_querylist.add(String.format("select distinct(mt.mid) from tags t, movie_tags mt where mt.tid = t.tid and t.tagvalue = '%s'", movie_tags_option.getText()));
        }

        String mid_from_movie_tags_query;

        if (mid_from_movie_tags_querylist.isEmpty())
        {
            mid_from_movie_tags_query = "NULL";
        }
        else
        {
            mid_from_movie_tags_query = String.join(" " + andor_sql_type + " ", mid_from_movie_tags_querylist);
        }

        return mid_from_movie_tags_query;
    }

    private JLabel set_label(JLabel element, String label, Boolean border)
    {
        element = new JLabel(label, SwingConstants.CENTER);
        element.setBackground(blue);
        element.setForeground(Color.darkGray);

        if (border)
        {
            element.setBorder(BorderFactory.createLineBorder(Color.black));
        }

        element.setOpaque(true);
        element.setPreferredSize(new Dimension(0, 40));
        return element;
    }

    private void set_height(JComponent component, int height)
    {
        Dimension d = component.getPreferredSize();
        d.height = height;
        component.setPreferredSize(d);
    }

    private void set_width(JComponent component, int width)
    {
        Dimension d = component.getPreferredSize();
        d.width = width;
        component.setPreferredSize(d);
    }

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

    private hw3(String title)
    {
        setTitle(title);
        load_interface();
        
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }

        conn = oracle_connect();

        load_genres();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                new hw3("COEN 280 - Harshavardhan Srinivas");
            }
        });
    }

}
