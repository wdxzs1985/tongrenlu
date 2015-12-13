CREATE 
VIEW `v_user_music_count` AS
    select 
        `m_user`.`id` AS `userBean.id`,
        count(`m_article`.`id`) AS `cnt`
    from
        ((`m_article`
        left join `r_music` ON ((`m_article`.`id` = `r_music`.`id`)))
        left join `m_user` ON ((`m_article`.`user_id` = `m_user`.`id`)))
    where
        ((`m_article`.`del_flg` = '0')
            and (`m_article`.`publish_flg` = '1')
            and (`r_music`.`del_flg` = '0')
            and (`m_user`.`id` is not null))
    group by `m_user`.`id`