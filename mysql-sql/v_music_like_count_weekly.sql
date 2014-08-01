CREATE 
VIEW `v_music_like_count_weekly` AS
    select 
        `r_like`.`like_id` AS `like_id`,
        count(`r_like`.`id`) AS `cnt`
    from
        `r_like`
    where
        ((`r_like`.`upd_date` > (curdate() - interval 7 day))
            and (`r_like`.`del_flg` = '0')
            and (`r_like`.`category` = 'm'))
    group by `r_like`.`like_id`
    order by count(`r_like`.`id`) desc