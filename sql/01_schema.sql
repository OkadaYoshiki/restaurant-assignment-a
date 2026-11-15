-- =====================================================================
-- 注文管理アプリ テーブル作成SQL
--   MySQL Workbench などで、02_data.sql より先に実行してください。
--   ※ 既存のテーブルは削除して作り直します（データも消えます）
-- =====================================================================

USE restaurant;

-- 外部キーで参照している側から順に削除する
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS dining_tables;
DROP TABLE IF EXISTS menu_items;

-- ---------------------------------------------------------------------
-- メニュー
-- ---------------------------------------------------------------------
CREATE TABLE menu_items (
    id        BIGINT      NOT NULL AUTO_INCREMENT  COMMENT 'メニューID',
    name      VARCHAR(50) NOT NULL                 COMMENT 'メニュー名',
    category  VARCHAR(20) NOT NULL                 COMMENT '種類（MAIN / SIDE / DRINK / DESSERT）',
    price     INT         NOT NULL                 COMMENT '価格（税抜き・円）',
    available BOOLEAN     NOT NULL DEFAULT TRUE    COMMENT 'TRUE:販売中 / FALSE:品切れ',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'メニュー';

-- ---------------------------------------------------------------------
-- 席
-- ---------------------------------------------------------------------
CREATE TABLE dining_tables (
    id     BIGINT      NOT NULL AUTO_INCREMENT     COMMENT '席ID',
    name   VARCHAR(20) NOT NULL                    COMMENT '席の名前（例：A1）',
    seats  INT         NOT NULL                    COMMENT '座れる人数',
    status VARCHAR(20) NOT NULL DEFAULT 'EMPTY'    COMMENT '状態（EMPTY:空席 / IN_USE:利用中）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dining_tables_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '席';

-- ---------------------------------------------------------------------
-- 注文（来店1回分。伝票1枚のイメージ）
--   会計前は paid_at・subtotal・tax・total が NULL
-- ---------------------------------------------------------------------
CREATE TABLE orders (
    id          BIGINT      NOT NULL AUTO_INCREMENT  COMMENT '注文ID',
    table_id    BIGINT      NOT NULL                 COMMENT '席ID',
    guest_count INT         NOT NULL                 COMMENT '人数',
    status      VARCHAR(20) NOT NULL DEFAULT 'OPEN'  COMMENT '状態（OPEN:来店中 / PAID:会計済）',
    opened_at   DATETIME    NOT NULL                 COMMENT '来店日時',
    paid_at     DATETIME                             COMMENT '会計日時',
    subtotal    INT                                  COMMENT '小計（税抜き）',
    tax         INT                                  COMMENT '消費税',
    total       INT                                  COMMENT '合計（税込み）',
    PRIMARY KEY (id),
    CONSTRAINT fk_orders_table FOREIGN KEY (table_id) REFERENCES dining_tables (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '注文';

-- ---------------------------------------------------------------------
-- 注文明細（1件の注文に、料理が何品もぶら下がる）
--   unit_price は注文したときのメニューの価格をコピーしたもの
-- ---------------------------------------------------------------------
CREATE TABLE order_items (
    id           BIGINT      NOT NULL AUTO_INCREMENT    COMMENT '明細ID',
    order_id     BIGINT      NOT NULL                   COMMENT '注文ID',
    menu_item_id BIGINT      NOT NULL                   COMMENT 'メニューID',
    quantity     INT         NOT NULL                   COMMENT '数量',
    unit_price   INT         NOT NULL                   COMMENT '注文時の単価（税抜き）',
    status       VARCHAR(20) NOT NULL DEFAULT 'ORDERED' COMMENT '状態（ORDERED:注文済 / SERVED:提供済）',
    ordered_at   DATETIME    NOT NULL                   COMMENT '注文日時',
    served_at    DATETIME                               COMMENT '提供日時',
    PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)     REFERENCES orders (id),
    CONSTRAINT fk_order_items_menu  FOREIGN KEY (menu_item_id) REFERENCES menu_items (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '注文明細';
