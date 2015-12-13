CREATE 
VIEW `v_user_follow_count` AS
    select 
        `r_like`.`user_id` AS `followerId`,
        count(`r_like`.`id`) AS `cnt`
    from
        `r_like`
    where
        ((`r_like`.`category` = 'u')
            and (`r_like`.`del_flg` = '0'))
    group by `r_like`.`user_id`;
