package hit_data_interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mukama
 */
public class Helper {

    /**
     * @param args the command line arguments
     */
    public Boolean transferDataToMySQL(String filePath) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/hit_interpreter_db?autoReconnect=true&useSSL=false", "root", "root@1");
            con.setAutoCommit(false);
            PreparedStatement pstm = null;

            FileInputStream ExcelFileToRead = new FileInputStream(filePath);
            //XSSF used for excel above 2007 and HSSF for excel below 2007
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

            XSSFWorkbook test = new XSSFWorkbook();

            XSSFSheet sheet = wb.getSheetAt(0);
//            XSSFRow row;
//            XSSFCell cell;

            DataFormatter formatter = new DataFormatter();
            int tableLopper = 6;
            int y = 0;

            ArrayList<String> indicator_category = new ArrayList<String>();
            ArrayList<String> indicator = new ArrayList<String>();
            ArrayList<String> shortened_indicator = new ArrayList<String>();
            ArrayList<String> graph_indicator = new ArrayList<String>();
            ArrayList<String> year = new ArrayList<String>();
            ArrayList<String> sex = new ArrayList<String>();
            ArrayList<String> race = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> place = new ArrayList<String>();
            ArrayList<String> bchc = new ArrayList<String>();
            ArrayList<String> source = new ArrayList<String>();
            ArrayList<String> methods = new ArrayList<String>();
            ArrayList<String> notes = new ArrayList<String>();
            ArrayList<String> low_90 = new ArrayList<String>();
            ArrayList<String> high_90 = new ArrayList<String>();
            ArrayList<String> low_95 = new ArrayList<String>();
            ArrayList<String> high_95 = new ArrayList<String>();

            //data that doesnt need to filtered.
            ArrayList<String> unfilter_indicators = new ArrayList<String>();
            ArrayList<String> unfilter_sex = new ArrayList<String>();
            ArrayList<String> unfilter_place = new ArrayList<String>();
            ArrayList<String> unfilter_race = new ArrayList<String>();

            XSSFCell category_name = null;
            XSSFCell indicator_ = null;
            XSSFCell shortened_indicator_ = null;
            XSSFCell graph_indicator_ = null;
            XSSFCell year_ = null;
            XSSFCell sex_ = null;
            XSSFCell race_ = null;
            XSSFCell value_ = null;
            XSSFCell place_ = null;
            XSSFCell bchc_ = null;
            XSSFCell source_ = null;
            XSSFCell methods_ = null;
            XSSFCell notes_ = null;
            XSSFCell low_90_ = null;
            XSSFCell high_90_ = null;
            XSSFCell low_95_ = null;
            XSSFCell high_95_ = null;

            //cell data that doesnt need to be filtered
            XSSFCell unfilter_indicators_ = null;
            XSSFCell unfilter_sex_ = null;
            XSSFCell unfilter_place_ = null;
            XSSFCell unfilter_race_ = null;
//this looks at row
            for (Iterator iterator = sheet.rowIterator(); iterator.hasNext();) {

                XSSFRow row = (XSSFRow) iterator.next();

                for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
                    category_name = row.getCell(0);
                    indicator_ = row.getCell(1);
                    shortened_indicator_ = row.getCell(2);
                    graph_indicator_ = row.getCell(3);
                    year_ = row.getCell(4);
                    sex_ = row.getCell(5);
                    race_ = row.getCell(6);
                    value_ = row.getCell(7);
                    place_ = row.getCell(8);
                    bchc_ = row.getCell(9);
                    source_ = row.getCell(10);
                    methods_ = row.getCell(11);
                    notes_ = row.getCell(12);
                    low_90_ = row.getCell(13);
                    high_90_ = row.getCell(14);
                    low_95_ = row.getCell(15);
                    high_95_ = row.getCell(16);

                    //we will not check for duplicates in this data
                    unfilter_indicators_ = row.getCell(1);
                    unfilter_sex_ = row.getCell(5);
                    unfilter_place_ = row.getCell(8);
                    unfilter_race_ = row.getCell(6);

                    //System.out.println(formatter.formatCellValue(category_name));
                }

                indicator_category.add(formatter.formatCellValue(category_name));
                indicator.add(formatter.formatCellValue(indicator_));
                shortened_indicator.add(formatter.formatCellValue(shortened_indicator_));
                graph_indicator.add(formatter.formatCellValue(graph_indicator_));
                year.add(formatter.formatCellValue(year_));
                sex.add(formatter.formatCellValue(sex_));
                race.add(formatter.formatCellValue(race_));
                value.add(formatter.formatCellValue(value_));
                place.add(formatter.formatCellValue(place_));
                bchc.add(formatter.formatCellValue(bchc_));
                source.add(formatter.formatCellValue(source_));
                methods.add(formatter.formatCellValue(methods_));
                notes.add(formatter.formatCellValue(notes_));
                low_90.add(formatter.formatCellValue(low_90_));
                high_90.add(formatter.formatCellValue(high_90_));
                low_95.add(formatter.formatCellValue(low_95_));
                high_95.add(formatter.formatCellValue(high_95_));

                //this data will not be checked for duplicates
                unfilter_indicators.add(formatter.formatCellValue(indicator_));
                unfilter_sex.add(formatter.formatCellValue(sex_));
                unfilter_race.add(formatter.formatCellValue(race_));
                unfilter_place.add(formatter.formatCellValue(place_));
            }

            //remove duplicate information from the ArrayList for indicators
            ArrayList<String> new_category_list = new ArrayList<String>();
            for (String emp : indicator_category) {
                if (!new_category_list.contains(emp)) {
                    new_category_list.add(emp);
                }
            }

            ArrayList<String> new_race_list = new ArrayList<String>();
            for (String emp : race) {
                if (!new_race_list.contains(emp)) {
                    new_race_list.add(emp);
                }
            }

            ArrayList<String> new_sex_list = new ArrayList<String>();
            for (String emp : sex) {
                if (!new_sex_list.contains(emp)) {
                    new_sex_list.add(emp);
                }
            }

            ArrayList<String> new_places_list = new ArrayList<String>();
            for (String emp : place) {
                if (!new_places_list.contains(emp)) {
                    new_places_list.add(emp);
                }
            }

            ArrayList<String> new_indicator_list = new ArrayList<String>();
            for (String emp : indicator) {
                if (!new_indicator_list.contains(emp)) {
                    new_indicator_list.add(emp);
                }
            }

            ArrayList<String> new_shortened_list = new ArrayList<String>();
            for (String emp : place) {
                if (!new_shortened_list.contains(emp)) {
                    new_shortened_list.add(emp);
                }
            }

            /*
        * insert data in the table.
             */
            for (int x = 0; x < tableLopper; x++) {
                if (x == 0) {
                    /*
                    insert the categories
                     */
                    for (int xy = 1; xy < new_category_list.size(); xy++) {
                        //System.out.println(new_category_list.get(xy));
                        String sql = "INSERT INTO tbl_indicator_categories (category_name) VALUES('" + new_category_list.get(xy) + "')";
                        pstm = (PreparedStatement) con.prepareStatement(sql);
                        pstm.execute();
                    }
                } else if (x == 1) {
                    /*
                    insert the race
                     */
                    for (int xy = 1; xy < new_race_list.size(); xy++) {
                        //System.out.println(new_category_list.get(xy));
                        String sql = "INSERT INTO tbl_race (race_name) VALUES ('" + new_race_list.get(xy) + "')";
                        pstm = (PreparedStatement) con.prepareStatement(sql);
                        pstm.execute();
                    }
                } else if (x == 2) {
                    /*
                    insert the sex
                     */
                    for (int xy = 1; xy < new_sex_list.size(); xy++) {
                        //System.out.println(new_category_list.get(xy));
                        String sql = "INSERT INTO tbl_sex (sex_name) VALUES ('" + new_sex_list.get(xy) + "')";
                        pstm = (PreparedStatement) con.prepareStatement(sql);
                        pstm.execute();
                    }
                } else if (x == 3) {
                    /*
                    insert the places
                     */
                    for (int xy = 1; xy < new_places_list.size(); xy++) {
                        //System.out.println(new_category_list.get(xy));
                        String sql = "INSERT INTO tbl_places (place_name) VALUES ('" + new_places_list.get(xy) + "')";
                        pstm = (PreparedStatement) con.prepareStatement(sql);
                        pstm.execute();
                    }
                } else if (x == 4) {
                    /*
                    insert into the table for indicators
                     */
                    for (int xy = 1; xy < new_indicator_list.size(); xy++) {
                        if (xy < 12) {
                            String sqlForeignId = "select * from tbl_indicator_categories where category_name='" + new_category_list.get(xy) + "'";
                            int foreignId = returnForeignKeyId(sqlForeignId, "category_id");
                            String sql = "INSERT INTO tbl_indicators (indicator_name,shortened_indicator_name,category_id) VALUES('" + new_indicator_list.get(xy) + "','" + new_shortened_list.get(xy) + "','" + foreignId + "')";
                            pstm = (PreparedStatement) con.prepareStatement(sql);
                            pstm.execute();
                        }
                    }
                } else if (x == 5) {
                    ArrayList<String> storeQueries = new ArrayList<String>();
                    for (int xy = 1; xy < graph_indicator.size(); xy++) {
                        if (xy < 30) {
                            //pick indicator_id
                            String sqlIndicatorId = "select * from tbl_indicators where indicator_name='" + unfilter_indicators.get(xy) + "'";
                            int indicatorId = returnForeignKeyId(sqlIndicatorId, "indicator_id");

                            //pick race_id
                            String sqlRaceId = "select * from tbl_race where race_name='" + unfilter_race.get(xy) + "'";
                            int raceId = returnForeignKeyId(sqlRaceId, "race_id");

                            //pick place_id
                            String sqlPlaceId = "select * from 	tbl_places where place_name='" + unfilter_place.get(xy) + "'";
                            int placeId = returnForeignKeyId(sqlPlaceId, "place_id");

                            //pick sex_id
                            String sqlSexId = "select * from tbl_sex where sex_name='" + unfilter_sex.get(xy) + "'";
                            int sexId = returnForeignKeyId(sqlSexId, "sex_id");

                            String sql = "INSERT INTO tbl_bchi_platform (graph_indicator_name,value,year,bchc_request_methodology,source,method,note,confidence_90_low,"
                                    + "confidence_90_high,confidence_95_low,confidence_95_high,race_id,place_id,sex_id,indicator_id) VALUES"
                                    + "('" + graph_indicator.get(xy) + "','" + value.get(xy) + "','" + year.get(xy) + "','" + bchc.get(xy) + "','" + source.get(xy) + "','"
                                    + methods.get(xy) + "','" + notes.get(xy) + "','" + low_90.get(xy) + "','" + high_90.get(xy) + "','" + low_95.get(xy)
                                    + "','" + high_95.get(xy) + "','" + raceId + "','" + placeId + "','" + sexId + "','" + indicatorId + "')";
                            storeQueries.add(sql);

                            pstm = (PreparedStatement) con.prepareStatement(sql);
                            pstm.execute();
                        }
                    }
                }

            }
            con.commit();
            pstm.close();
            con.close();
            ExcelFileToRead.close();
            System.out.println("Excel file has been imported into MySQL database.");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println(e);
        }
        return true;
    }

    public int returnForeignKeyId(String sql, String whereId) {
        int foreignId = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/hit_interpreter_db?autoReconnect=true&useSSL=false", "root", "root@1");
            con.setAutoCommit(false);
            PreparedStatement pstm = null;
            pstm = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                foreignId = rs.getInt(whereId);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return foreignId;
    }

}
