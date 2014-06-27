CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `tongrenlu`@`%` 
    SQL SECURITY DEFINER
VIEW `v_user_music_count` AS
    select 
        count(`v_music`.`id`) AS `cnt`,
        `v_music`.`userBean.id` AS `userId`
    from
        `v_music`
    group by `v_music`.`userBean.id`