package com.lj.dao.imp;/**
 * Created by JuN on 17/1/24.
 */

import com.lj.dao.IMusicDao;
import com.lj.entity.JsonpFormat;
import com.lj.entity.Music;
import com.lj.entity.MusicEntity;
import com.lj.untity.CommonUntity;
import com.lj.untity.DBUnit;
import com.lj.untity.LoadHelper;
import com.lj.untity.PathUntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.*;

/**
 * dao
 *
 * @authorJuN
 * @create2017-01-24 23:17
 */
@Repository
public class MusicDao implements IMusicDao {

    @Autowired
    private DBUnit unit ;

    private Connection connection ;

    public ResponseEntity<byte[]> downloadFile(String fileName) {
        //从数据库中获取文件路径
        String musicPath ;
        int resultCode ;
        connection = unit.openConnection();
        String sql = "{call getMusicPath(?,?,?)}";
        try {
            CallableStatement statement = connection.prepareCall(sql);
            statement.setString(Music.MUSICNAME,fileName);
            statement.registerOutParameter(Music.RESULTCODE, Types.INTEGER);
            statement.registerOutParameter(Music.MUSICPATH,Types.VARCHAR);
            statement.execute();
            resultCode = statement.getInt(Music.RESULTCODE);
            musicPath = statement.getString(Music.MUSICPATH);
            statement.close();
        } catch (SQLException e) {
            resultCode = 101;
            musicPath = "";
            e.printStackTrace();
            unit.closeConnection(connection);
            return null;
        }
        unit.closeConnection(connection);
        if (resultCode != 1){
            System.out.println("数据库存储过程出错");
            return null;
        }
        //包装fileName 跟上后缀名
        int pointIndex = musicPath.lastIndexOf(".");
        String prefix = musicPath.substring(pointIndex);
        fileName = fileName + prefix;
        //下载文件
        return LoadHelper.download(fileName,new File(musicPath));
    }

    public String uploadFile(MultipartFile file,String fileName) {

        JSONObject resultObject = new JSONObject();


        String dfileName = file.getOriginalFilename();
        int pointIndex = dfileName.lastIndexOf(".");
        String prefix = dfileName.substring(pointIndex);

        //fileName 转化为路径
        String filePath = PathUntity.MUSIC_SAVE_ADDRESS+fileName+prefix;

        String resultCode = LoadHelper.upload(file,filePath);
        int code ;
        if (resultCode.equals(CommonUntity.SUCCESS)){
            //添加新音乐的记录到数据库
            connection = unit.openConnection();
            String sql = "{call addMusic(?,?,?)}";
            try {
                CallableStatement statement = connection.prepareCall(sql);
                statement.setString(Music.MUSICNAME,fileName);
                statement.setString(Music.MUSICPATH,filePath);
                statement.registerOutParameter(Music.RESULTCODE,Types.INTEGER);
                statement.execute();
                code = statement.getInt(Music.RESULTCODE);
                statement.close();
            } catch (SQLException e) {
                code = 101 ;
                e.printStackTrace();
            }
            unit.closeConnection(connection);
        }else {
            code = 102 ;
        }
        resultObject.put("resultCode",code);

        return resultObject.toString();
    }

//    public String uploadFile(MultipartFile file, String fileName, String callback) {
//        return callback+JsonpFormat.prefix_callback+uploadFile(file,fileName)+JsonpFormat.suffix_callback;
//    }

//    public String uploadFiles(MultipartFile[] files, String[] fileNames, String callback) {
//        return callback+JsonpFormat.prefix_callback+uploadFiles(files,fileNames)+JsonpFormat.suffix_callback;
//    }

    public String uploadFiles(MultipartFile[] files, String[] fileNames) {
        JSONObject resultObject = new JSONObject();

        Integer[] codes = new Integer[fileNames.length];
        int[] result = {1,2};
        String[] filePaths = new String[fileNames.length];
        for (int i = 0 ;i < files.length ; i++){

            if (!files[i].isEmpty()){
                String dfileName = files[i].getOriginalFilename();
                int pointIndex = dfileName.lastIndexOf(".");
                String prefix = dfileName.substring(pointIndex);

                String filePath = PathUntity.MUSIC_SAVE_ADDRESS+fileNames[i]+prefix;
                filePaths[i] = filePath;
            }else {
                filePaths[i] = "";
            }

//            System.out.println("----"+fileNames[i]);

        }
        String resultCode = LoadHelper.uploads(files,filePaths);

        if (resultCode.equals(CommonUntity.SUCCESS)){
            connection = unit.openConnection();
            for (int i = 0 ;i < files.length ;i++){
                if (!files[i].isEmpty()){
                    String sql = "{call addMusic(?,?,?)}";
                    try {
                        CallableStatement statement = connection.prepareCall(sql);
                        statement.setString(Music.MUSICNAME,fileNames[i]);
                        statement.setString(Music.MUSICPATH,filePaths[i]);
                        statement.registerOutParameter(Music.RESULTCODE,Types.INTEGER);
                        statement.execute();
                        codes[i] = statement.getInt(Music.RESULTCODE);
                        statement.close();
                        if (!element(codes[i],result)){
                            codes[i] = 101 ;
                        }
                    } catch (SQLException e) {
                        codes[i] = 101;
                        e.printStackTrace();
                    }
                }else {
                    codes[i] = 102 ;
                }
            }
            unit.closeConnection(connection);
        }else if (resultCode.equals(CommonUntity.FILE_ARE_ALL_NULL)){
            for (int code : codes){
                code = 102 ;
            }
        }

        JSONArray array = new JSONArray();
        for (int i = 0 ;i < codes.length ;i++){
            JSONObject item = new JSONObject();
            item.put("fileName",fileNames[i]);
            item.put("resultCode",codes[i]);
            array.put(item);
        }
        resultObject.put("results",array);

        return resultObject.toString();
    }

//    public String listAllMusic(String callback) {
//        return callback+JsonpFormat.prefix_callback+listAllMusic()+JsonpFormat.suffix_callback;
//    }

    public String listAllMusic() {
        JSONObject resultObject = new JSONObject();
        JSONArray array = new JSONArray();
        connection = unit.openConnection();
        String sql = "{call listMusic(?)}";
        int resultCode ;
        try {
            CallableStatement statement = connection.prepareCall(sql);
            statement.registerOutParameter(Music.RESULTCODE,Types.INTEGER);
            statement.execute();
            resultCode = statement.getInt(Music.RESULTCODE);

            if (resultCode == 1){
                ResultSet set = statement.getResultSet();
                while (set.next()){
                    JSONObject item = new JSONObject();
                    int id = set.getInt(MusicEntity.ID);
                    String name = set.getString(MusicEntity.NAME);
                    String path = set.getString(MusicEntity.PATH);

                    int pointIndex = path.lastIndexOf(".");
                    String prefix = path.substring(pointIndex);
                    String fullName = name + prefix ;

                    item.put("id",id);
                    item.put("name",fullName);

                    array.put(item);

                }
            }
            statement.close();


        } catch (SQLException e) {
            resultCode = 101 ;
            e.printStackTrace();
        }
        unit.closeConnection(connection);
        resultObject.put("resultCode",resultCode);
        resultObject.put("results",array);

        return resultObject.toString();
    }

    private boolean element(int value, int[] elements){
        boolean belong = false ;
        for (int i = 0 ;i < elements.length ;i++){
            if (value == elements[i]){
                belong = true;
            }
        }
        return belong;
    }
}
