CREATE 
VIEW `v_user_follower_count` AS
    select 
        `r_like`.`like_id` AS `followId`,
        count(`r_like`.`id`) AS `cnt`
    from
        `r_like`
    where
        ((`r_like`.`category` = 'u')
            and (`r_like`.`del_flg` = '0'))
    group by `r_like`.`like_id`;
