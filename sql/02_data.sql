-- =====================================================================
-- 注文管理アプリ 初期データ
--   01_schema.sql を実行したあとに実行してください。
--   データを最初の状態に戻したいときは、01_schema.sql → 02_data.sql の順にもう一度実行します。
--   日時は「このSQLを実行した時刻」を基準に作ります。
-- =====================================================================

USE restaurant;

INSERT INTO menu_items (id, name, category, price, available) VALUES
 ( 1, 'ハンバーグ定食',   'MAIN',    980, TRUE),
 ( 2, 'からあげ定食',     'MAIN',    900, TRUE),
 ( 3, 'カレーライス',     'MAIN',    850, TRUE),
 ( 4, 'オムライス',       'MAIN',    880, TRUE),
 ( 5, 'ポテトフライ',     'SIDE',    400, TRUE),
 ( 6, 'シーザーサラダ',   'SIDE',    450, TRUE),
 ( 7, 'コーンスープ',     'SIDE',    300, FALSE),
 ( 8, 'ウーロン茶',       'DRINK',   300, TRUE),
 ( 9, 'コーラ',           'DRINK',   300, TRUE),
 (10, 'オレンジジュース', 'DRINK',   300, TRUE),
 (11, 'バニラアイス',     'DESSERT', 350, TRUE),
 (12, 'チョコパフェ',     'DESSERT', 600, TRUE);

INSERT INTO dining_tables (id, name, seats, status) VALUES
 (1, 'A1', 2, 'EMPTY'),
 (2, 'A2', 2, 'IN_USE'),
 (3, 'A3', 2, 'EMPTY'),
 (4, 'B1', 4, 'IN_USE'),
 (5, 'B2', 4, 'EMPTY'),
 (6, 'B3', 4, 'EMPTY');

-- 注文
--  1: B2 今日 会計済（ハンバーグ定食×2、ウーロン茶×2）
--  2: A3 昨日 会計済（カレーライスは値上げ前の800円で注文していた）
--  3: A2 来店中（2品提供済、ポテトフライがまだ）
--  4: B1 来店中（3品とも提供待ち）
INSERT INTO orders (id, table_id, guest_count, status, opened_at, paid_at, subtotal, tax, total) VALUES
 (1, 5, 3, 'PAID', NOW() - INTERVAL 90 MINUTE, NOW() - INTERVAL 30 MINUTE, 2560, 256, 2816),
 (2, 3, 2, 'PAID', CURDATE() - INTERVAL 1 DAY + INTERVAL 19 HOUR, CURDATE() - INTERVAL 1 DAY + INTERVAL 20 HOUR, 1150, 115, 1265),
 (3, 2, 2, 'OPEN', NOW() - INTERVAL 40 MINUTE, NULL, NULL, NULL, NULL),
 (4, 4, 3, 'OPEN', NOW() - INTERVAL 10 MINUTE, NULL, NULL, NULL, NULL);

INSERT INTO order_items (id, order_id, menu_item_id, quantity, unit_price, status, ordered_at, served_at) VALUES
 (1, 1,  1, 2, 980, 'SERVED',  NOW() - INTERVAL 85 MINUTE, NOW() - INTERVAL 70 MINUTE),
 (2, 1,  8, 2, 300, 'SERVED',  NOW() - INTERVAL 85 MINUTE, NOW() - INTERVAL 80 MINUTE),
 (3, 2,  3, 1, 800, 'SERVED',  CURDATE() - INTERVAL 1 DAY + INTERVAL 19 HOUR + INTERVAL 5 MINUTE, CURDATE() - INTERVAL 1 DAY + INTERVAL 19 HOUR + INTERVAL 20 MINUTE),
 (4, 2, 11, 1, 350, 'SERVED',  CURDATE() - INTERVAL 1 DAY + INTERVAL 19 HOUR + INTERVAL 5 MINUTE, CURDATE() - INTERVAL 1 DAY + INTERVAL 19 HOUR + INTERVAL 40 MINUTE),
 (5, 3,  2, 1, 900, 'SERVED',  NOW() - INTERVAL 35 MINUTE, NOW() - INTERVAL 20 MINUTE),
 (6, 3,  9, 1, 300, 'SERVED',  NOW() - INTERVAL 35 MINUTE, NOW() - INTERVAL 33 MINUTE),
 (7, 3,  5, 1, 400, 'ORDERED', NOW() - INTERVAL 15 MINUTE, NULL),
 (8, 4,  1, 3, 980, 'ORDERED', NOW() - INTERVAL 8 MINUTE,  NULL),
 (9, 4, 10, 3, 300, 'ORDERED', NOW() - INTERVAL 8 MINUTE,  NULL);
