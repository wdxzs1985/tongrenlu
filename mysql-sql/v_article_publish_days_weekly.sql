CREATE 
VIEW `v_article_publish_days_weekly` AS
    select 
        `m_article`.`id` AS `id`,
        (to_days(`m_article`.`publish_date`) - to_days(curdate())) AS `days`
    from
        `m_article`
    where
        ((`m_article`.`publish_date` > (curdate() - interval 7 day))
            and (`m_article`.`del_flg` = '0'))
    order by (to_days(`m_article`.`publish_date`) - to_days(curdate())) desc