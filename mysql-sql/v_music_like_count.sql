CREATE 
VIEW `v_music_like_count` AS
    select 
        `r_like`.`like_id` AS `articleId`,
        count(`r_like`.`id`) AS `cnt`
    from
        `r_like`
    where
        ((`r_like`.`category` = 'm')
            and (`r_like`.`del_flg` = '0'))
    group by `r_like`.`like_id`;
