CREATE
VIEW `v_article_comment_count` AS
    select 
        `m_comment`.`article_id` AS `articleId`,
        count(`m_comment`.`id`) AS `cnt`
    from
        `m_comment`
    where
        (`m_comment`.`del_flg` = '0')
    group by `m_comment`.`article_id`;
