CREATE 
VIEW `v_music_ranking` AS
    select 
        `v_music`.`id` AS `id`,
        `v_music`.`title` AS `title`,
        `v_music`.`description` AS `description`,
        `v_music`.`publishFlg` AS `publishFlg`,
        `v_music`.`publishDate` AS `publishDate`,
        `v_music`.`userBean.id` AS `userBean.id`,
        `v_music`.`userBean.nickname` AS `userBean.nickname`,
        `v_music`.`userBean.signature` AS `userBean.signature`,
        `v_music`.`accessCount` AS `accessCount`,
        `v_music`.`likeCount` AS `likeCount`,
        `v_music`.`commentCount` AS `commentCount`,
        (ifnull(`v_article_comment_count_weekly`.`cnt`,
                0) * 5) AS `pt1`,
        0 AS `pt2`,
        ((ifnull(`v_article_publish_days_weekly`.`days`,
                -(7)) + 7) * 2) AS `pt3`,
        ((ifnull(`v_article_comment_count_weekly`.`cnt`,
                0) * 5) + ((ifnull(`v_article_publish_days_weekly`.`days`,
                -(7)) + 7) * 2)) AS `pt`
    from
        ((`v_music`
        left join `v_article_comment_count_weekly` ON ((`v_music`.`id` = `v_article_comment_count_weekly`.`article_id`)))
        left join `v_article_publish_days_weekly` ON ((`v_music`.`id` = `v_article_publish_days_weekly`.`id`)))
    where
        (`v_music`.`publishFlg` in ('1' , '2'))
    order by ((ifnull(`v_article_comment_count_weekly`.`cnt`,
            0) * 5) + ((ifnull(`v_article_publish_days_weekly`.`days`,
            -(7)) + 7) * 2)) desc , `v_music`.`publishDate` desc