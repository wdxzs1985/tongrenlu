CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `v_user_music_count` AS
    select 
        `m_user`.`id` AS `userBean.id`,
        count(`v_music`.`id`) AS `cnt`
    from
        (`v_music`
        left join `m_user` ON ((`v_music`.`userBean.id` = `m_user`.`id`)))
    group by `m_user`.`id`;
