package com.lj.untity;/**
 * Created by JuN on 17/1/24.
 */

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * connect to database unity
 *
 * @authorJuN
 * @create2017-01-24 22:56
 */
@Repository
public class DBUnit {
    public Connection openConnection(){

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ljMusic-mysql.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("读取配置文件失败");
            e.printStackTrace();
        }

        String jDriver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");


        return createConnection(jDriver,url,username,password);

    }

    //1表示成功，0表示原来连接为空，－1表示连接异常
    public int closeConnection(Connection connection){
        if (connection == null){
            return 0;
        }
        try {
            connection.close();
            return 1;
        } catch (SQLException e) {
            //System.out.println("数据库关闭异常");
            e.printStackTrace();
            return -1;
        }
    }


    private Connection createConnection(String className,String url,String name,String password){
        Connection connection = null;
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, name, password);
            //System.out.println("数据库连接成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //System.out.println("数据库驱动加载失败");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("数据库连接失败");
            return null;
        }
        return connection ;
    }
}
