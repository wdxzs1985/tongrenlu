CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `tongrenlu`@`%` 
    SQL SECURITY DEFINER
VIEW `v_user` AS
    select 
        `u`.`id` AS `id`,
        `u`.`nickname` AS `nickname`,
        `u`.`signature` AS `signature`,
        ifnull(`fc1`.`cnt`, 0) AS `followCount`,
        ifnull(`fc2`.`cnt`, 0) AS `followerCount`
    from
        ((`m_user` `u`
        left join `v_user_follow_count` `fc1` ON ((`u`.`id` = `fc1`.`followerId`)))
        left join `v_user_follower_count` `fc2` ON ((`u`.`id` = `fc2`.`followId`)))
    where
        (`u`.`del_flg` = '0')