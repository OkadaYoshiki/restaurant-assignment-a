# 注文管理アプリ 課題A【基礎】

飲食店の「席・注文・キッチン・会計・売上」を管理する Web アプリです（図書貸出管理アプリの次の課題）。
いくつかの機能が未実装になっているので、自分で実装して完成させましょう。

**課題の内容は [`課題A.md`](課題A.md) を読んでください。**

## 使用技術

- Java 17 以上
- Spring Boot 3.3（Spring Web / Thymeleaf / Spring Data JPA / Validation）
- MySQL 8
- Eclipse（Maven プロジェクトとして使います）

## 機能一覧

| No | 機能 | URL | 状態 |
|---|---|---|---|
| ① | メニュー一覧・種類で絞り込み | GET `/menu?category=...` | **未実装（課題A-1）** |
| ② | メニュー登録・編集 | GET/POST `/menu/new` | 実装済み |
| ③ | 品切れの切り替え | POST `/menu/{id}/toggle` | **未実装（課題A-2）** |
| ④ | 席の一覧・来店受付 | GET `/tables` | 実装済み |
| ⑤ | キッチン画面 | GET `/kitchen` | **未実装（課題A-3）** |
| ⑥ | 注文の追加 | POST `/orders/{id}/items` | 未実装（課題Bで作ります） |
| ⑦ | 会計 | GET/POST `/orders/{id}/checkout` | 未実装（課題Bで作ります） |
| ⑧ | 売上集計 | GET `/sales` | 未実装（課題Bで作ります） |

---

## セットアップ

### 1. MySQL Workbench でデータベースとテーブルを作る

1. root で接続し、データベースとユーザーを作る（最初の1回だけ）

   ```sql
   CREATE DATABASE restaurant CHARACTER SET utf8mb4;
   CREATE USER 'restaurant'@'localhost' IDENTIFIED BY 'restaurant';
   GRANT ALL PRIVILEGES ON restaurant.* TO 'restaurant'@'localhost';
   ```

2. 「File → Open SQL Script」で `sql/01_schema.sql` を開いて ⚡（Execute）で実行 → テーブルができる
3. 同じように `sql/02_data.sql` を実行 → 初期データが入る
4. 左の SCHEMAS で `restaurant` を右クリック →「Refresh All」。dining_tables / menu_items / order_items / orders が見えればOK

`01_schema.sql` か [`docs/テーブル定義書.md`](docs/テーブル定義書.md)（ER図付き）で、どんなテーブル・列があるか読んでおきましょう。
`entity/` の Java クラスと見比べると、クラスとテーブルの対応がわかります。

### 2. Eclipse に取り込む

1. 「ファイル → インポート → Git → Git からプロジェクト（スマート・インポート付き）」→「URI のクローン」
2. URI にこのリポジトリのURL（`https://github.com/OkadaYoshiki/restaurant-assignment-a.git`）を貼り付ける。ユーザーとパスワードは空欄のまま
3. ブランチ `main` のまま進み、保存先（普通は `ホーム/git/restaurant-assignment-a`）をメモして「完了」
4. ライブラリのダウンロードが終わるまで待つ。赤い×が出たら、プロジェクトを右クリック →「Maven → プロジェクトの更新」

### 3. 起動する

`src/main/java/com/example/restaurant/RestaurantApplication.java` を右クリック →「実行 → Java アプリケーション」

コンソールに `Started RestaurantApplication` と出たら、ブラウザで http://localhost:8080 を開きます。
止めるときはコンソールの赤い■を押してください。

### 注意

- 画面で登録・削除したデータは残ります。最初の状態に戻したいときは `sql/01_schema.sql` → `sql/02_data.sql` の順にもう一度実行してください。
- 未実装の機能の画面を開くと、エラー画面（404 や 500）になることがあります。実装すれば表示されるようになります。

## フォルダ構成

```
src/main/java/com/example/restaurant
├── controller/   画面のURLと処理の対応
├── service/      業務ロジック
├── repository/   DBアクセス（Spring Data JPA）
├── entity/       テーブルに対応するクラス
├── dto/          画面に渡すデータの入れ物
├── form/         画面の入力値を受け取るクラス
└── exception/    業務エラーの例外
docs/
└── テーブル定義書.md         ER図・テーブル定義書
sql/
├── 01_schema.sql            テーブル作成
└── 02_data.sql              初期データ
src/main/resources
├── application.properties   DB接続などの設定
└── templates/               画面（Thymeleaf）
```

## 提出のしかた

1. アプリを■で止める
2. 「ファイル → エクスポート → 一般 → アーカイブ・ファイル」
3. `restaurant-assignment-a` にチェックを入れ、中の `target` フォルダーのチェックだけ外す
4. `課題A_氏名.zip` という名前で保存し、講師が指定した提出先に提出する
