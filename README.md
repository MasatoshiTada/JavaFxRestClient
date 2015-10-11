# JavaFxRestClient
JavaFX 8 + JAX-RS Client APIで作ったRESTクライアントです。

## 使用技術
- JDK 1.8.0_60
- JavaFX 8
- JAX-RS Client API(jersey-client-2.22)
- Jackson 2.5.4

## 開発環境
- NetBeans 8.0.2
- JavaFX Scene Builder 8.0.0
- OS X El Capitan

## WebAPI仕様

### リクエスト例
GET /JavaFxRestClient_Server/api/employees?name=m HTTP/1.1

### レスポンス例
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
```
[ {
  "name" : "Yumi Wakatsuki",
  "emp_id" : 1,
  "joined_date" : 1427814000000,
  "department" : {
    "name" : "Sales",
    "dept_id" : 1
  }
}, {
  "name" : "Mai Fukagawa",
  "emp_id" : 2,
  "joined_date" : 1427814000000,
  "department" : {
    "name" : "Sales",
    "dept_id" : 1
  }
} ]
```