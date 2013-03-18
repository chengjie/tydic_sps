package com.tydic.sps.mapper;

import com.tydic.sps.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-13
 * Time: 下午12:57
 */
@Repository
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE USERID = #{id}")
    User findUserById(int id);
    @Select("SELECT * FROM USER")
    List<User> findUserList();
    @Insert("INSERT INTO USER (USERNAME, PASSWORD) VALUES (#{username}, #{password})")
    int insertUser(User user);

    /**
     * 查找3g应用
     * @return
     */
    @Select("select app_id, app_name, img from tb_ass_business_app")
    List<Map<String, Object>> find3G();

    /**
     * 更新3g应用图片
     * @param img 图片
     * @param appid 应用的ID
     * @return
     */
    @Insert("UPDATE tb_ass_business_app t SET t.IMG = #{img} WHERE t.APP_ID = #{appid}")
    int isertApp(@Param(value = "img") String img, @Param(value = "appid") String appid);

    /**
     * 插入资源表
     * @param name
     * @param path
     * @param width
     * @param height
     * @param size
     * @return
     */
    @Insert("INSERT INTO TB_ASS_CMS_RESOURCE(RES_ID, RES_NAME, RES_TYPE, PATH, EXT, WIDTH, HEIGHT, FILE_SIZE, CREATE_USER,CREATE_DATE, CLASSFY)" +
            "VALUES(SEQ_ASS_CMS_RESOURCE.NEXTVAL, #{name}, 1, #{path}, #{ext}, #{width}, #{height}, #{size}, 'root', sysdate, '3g')")
    int insertResource(@Param(value = "name") String name, @Param(value = "path") String path,
                       @Param(value = "ext") String ext,
                       @Param(value = "width")Double width,
                       @Param(value = "height") Double height, @Param(value = "size") Integer size);
}
