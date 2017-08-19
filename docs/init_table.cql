CREATE KEYSPACE demo WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', 'dc1' : 1};

CREATE TABLE demo.goods_by_uid(
    uid bigint,
    browse_time bigint,
    goods_id bigint,
    is_visible boolean,
    PRIMARY KEY (uid, browse_time, goods_id))
WITH CLUSTERING ORDER BY (browse_time DESC, goods_id DESC) ;

