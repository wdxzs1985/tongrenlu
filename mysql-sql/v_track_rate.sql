CREATE
VIEW `v_track_rate` AS
    select 
        `r_track_rate`.`track_id` AS `track_id`,
        avg(`r_track_rate`.`rate`) AS `rate`
    from
        `r_track_rate`
    where
        (`r_track_rate`.`del_flg` = '0')
    group by `r_track_rate`.`track_id`