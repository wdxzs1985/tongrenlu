CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `tongrenlu`@`%` 
    SQL SECURITY DEFINER
VIEW `v_timeline` AS
    select 
        `v_comic`.`id` AS `articleBean.id`,
        `v_comic`.`title` AS `articleBean.title`,
        `v_comic`.`userBean.id` AS `userBean.id`,
        `v_comic`.`userBean.nickname` AS `userBean.nickname`,
        `v_comic`.`description` AS `content`,
        `v_comic`.`publishDate` AS `createDate`,
        `v_comic`.`userBean.id` AS `followId`,
        NULL AS `followerId`,
        `v_comic`.`redFlg` AS `redFlg`,
        `v_comic`.`translateFlg` AS `translateFlg`,
        'publish_comic' AS `action`
    from
        `v_comic`
    where
        (`v_comic`.`publishFlg` = '1') 
    union select 
        `v_comic_comment`.`articleBean.id` AS `articleBean.id`,
        `v_comic_comment`.`articleBean.title` AS `articleBean.title`,
        `v_comic_comment`.`userBean.id` AS `userBean.id`,
        `v_comic_comment`.`userBean.nickname` AS `userBean.nickname`,
        `v_comic_comment`.`content` AS `content`,
        `v_comic_comment`.`createDate` AS `createDate`,
        `v_comic_comment`.`userBean.id` AS `followId`,
        `v_comic_comment`.`articleBean.userBean.id` AS `followerId`,
        `v_comic_comment`.`redFlg` AS `redFlg`,
        `v_comic_comment`.`translateFlg` AS `translateFlg`,
        'comment_comic' AS `action`
    from
        `v_comic_comment` 
    union select 
        `v_music`.`id` AS `articleBean.id`,
        `v_music`.`title` AS `articleBean.title`,
        `v_music`.`userBean.id` AS `userBean.id`,
        `v_music`.`userBean.nickname` AS `userBean.nickname`,
        `v_music`.`description` AS `content`,
        `v_music`.`publishDate` AS `createDate`,
        `v_music`.`userBean.id` AS `followId`,
        NULL AS `followerId`,
        NULL AS `redFlg`,
        NULL AS `translateFlg`,
        'publish_music' AS `action`
    from
        `v_music`
    where
        (`v_music`.`publishFlg` = '1') 
    union select 
        `v_music_comment`.`articleBean.id` AS `articleBean.id`,
        `v_music_comment`.`articleBean.title` AS `articleBean.title`,
        `v_music_comment`.`userBean.id` AS `userBean.id`,
        `v_music_comment`.`userBean.nickname` AS `userBean.nickname`,
        `v_music_comment`.`content` AS `content`,
        `v_music_comment`.`createDate` AS `createDate`,
        `v_music_comment`.`userBean.id` AS `followId`,
        `v_music_comment`.`articleBean.userBean.id` AS `followerId`,
        NULL AS `redFlg`,
        NULL AS `translateFlg`,
        'comment_music' AS `action`
    from
        `v_music_comment` 
    union select 
        NULL AS `articleBean.id`,
        NULL AS `articleBean.title`,
        `v_user`.`id` AS `userBean.id`,
        `v_user`.`nickname` AS `userBean.nickname`,
        NULL AS `content`,
        `r_like`.`upd_date` AS `createDate`,
        NULL AS `followId`,
        `r_like`.`user_id` AS `followerId`,
        NULL AS `redFlg`,
        NULL AS `translateFlg`,
        'follow' AS `action`
    from
        (`v_user`
        join `r_like` ON ((`r_like`.`like_id` = `v_user`.`id`)))
    where
        ((`r_like`.`category` = 'u')
            and (`r_like`.`del_flg` = '0'))