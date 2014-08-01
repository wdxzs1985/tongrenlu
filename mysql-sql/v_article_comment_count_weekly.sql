CREATE 
VIEW `v_article_comment_count_weekly` AS
    select 
        `m_comment`.`article_id` AS `article_id`,
        count(`m_comment`.`id`) AS `cnt`
    from
        `m_comment`
    where
        ((`m_comment`.`upd_date` > (curdate() - interval 7 day))
            and (`m_comment`.`del_flg` = '0'))
    group by `m_comment`.`article_id`
    order by count(`m_comment`.`id`) desc