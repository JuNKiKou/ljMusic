package com.lj.service.imp;/**
 * Created by JuN on 17/1/27.
 */

import com.lj.dao.IMusicDao;
import com.lj.service.IListMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * list music service
 *
 * @authorJuN
 * @create2017-01-27 14:43
 */
@Service
public class ListMusicService implements IListMusicService{

    @Autowired
    private IMusicDao dao ;

//    public String listAllMusic(String callback) {
//        return dao.listAllMusic(callback);
//    }

    public String listAllMusic() {
        return dao.listAllMusic();
    }
}
