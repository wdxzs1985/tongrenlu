CREATE 
VIEW `v_user` AS
    select 
        `u`.`id` AS `id`,
        `u`.`nickname` AS `nickname`,
        `u`.`signature` AS `signature`,
        ifnull(`mc`.`cnt`, 0) AS `musicCount`,
        ifnull(`fc1`.`cnt`, 0) AS `followCount`,
        ifnull(`fc2`.`cnt`, 0) AS `followerCount`
    from
        `m_user` `u`
        left join `v_user_music_count` `mc` ON (`u`.`id` = `mc`.`userBean.id`)
        left join `v_user_follow_count` `fc1` ON (`u`.`id` = `fc1`.`followerId`)
        left join `v_user_follower_count` `fc2` ON (`u`.`id` = `fc2`.`followId`)
    where
        (`u`.`del_flg` = '0');
