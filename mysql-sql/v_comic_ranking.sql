CREATE
VIEW `v_comic_ranking` AS
    select 
        `v_comic`.`id` AS `id`,
        `v_comic`.`title` AS `title`,
        `v_comic`.`description` AS `description`,
        `v_comic`.`publishFlg` AS `publishFlg`,
        `v_comic`.`publishDate` AS `publishDate`,
        `v_comic`.`recommendFlg` AS `recommendFlg`,
        `v_comic`.`redFlg` AS `redFlg`,
        `v_comic`.`translateFlg` AS `translateFlg`,
        `v_comic`.`accessCount` AS `accessCount`,
        `v_comic`.`likeCount` AS `likeCount`,
        `v_comic`.`commentCount` AS `commentCount`,
        (ifnull(`v_article_comment_count_weekly`.`cnt`,
                0) * 10) AS `pt1`,
        (ifnull(`v_comic_like_count_weekly`.`cnt`, 0) * 5) AS `pt2`,
        ((ifnull(`v_article_publish_days_weekly`.`days`,
                -(7)) + 7) * 5) AS `pt3`,
        (((ifnull(`v_article_comment_count_weekly`.`cnt`,
                0) * 10) + (ifnull(`v_comic_like_count_weekly`.`cnt`, 0) * 5)) + ((ifnull(`v_article_publish_days_weekly`.`days`,
                -(7)) + 7) * 5)) AS `pt`
    from
        (((`v_comic`
        left join `v_article_comment_count_weekly` ON ((`v_comic`.`id` = `v_article_comment_count_weekly`.`article_id`)))
        left join `v_comic_like_count_weekly` ON ((`v_comic`.`id` = `v_comic_like_count_weekly`.`like_id`)))
        left join `v_article_publish_days_weekly` ON ((`v_comic`.`id` = `v_article_publish_days_weekly`.`id`)))
    where
        ((`v_comic`.`publishFlg` = '1')
            and (`v_comic`.`redFlg` = '0'))
    order by (((ifnull(`v_article_comment_count_weekly`.`cnt`,
            0) * 10) + (ifnull(`v_comic_like_count_weekly`.`cnt`, 0) * 5)) + ((ifnull(`v_article_publish_days_weekly`.`days`,
            -(7)) + 7) * 5)) desc , `v_comic`.`publishDate` desc