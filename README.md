# Spring CQRS


Một ví dụ đơn giản về ứng dụng **CQRS** (Command--Query Responsibility
Segregation) sử dụng Spring Boot, để minh hoạ cách tách biệt mô hình ghi
(command / write‑model) và mô hình đọc (query / read‑model), không dùng
Event Store, phù hợp để học / thử nghiệm.

## 🎯 Mục tiêu

-   Tách biệt rõ **Write Model** (command) và **Read Model** (query)
    theo CQRS.
-   Sử dụng **Value Objects (VO)** để validate dữ liệu trước khi tạo
    Entity.
-   Dễ dàng mở rộng: có thể bổ sung asynchronous projection, message
    queue, event‑driven, microservice, v.v.
-   Phù hợp làm demo, thử nghiệm hoặc khởi tạo kiến trúc microservice
    chuẩn.

## 📁 Cấu trúc dự án

    / (root)
    ├── src/main/java/.../command         
    │   ├── controller                   
    │   ├── service                      
    │   ├── model                        
    │   ├── vo                           
    │   └── dto                          
    │
    ├── src/main/java/.../query           
    │   ├── controller                   
    │   ├── model                        
    │   ├── repository                   
    │   └── dto                          
    │
    ├── src/main/java/.../events          
    ├── src/main/resources                

## 🚀 Cài đặt & chạy

1.  `git clone https://github.com/nguyenlyminhman/spring.cqrs.git`

2.  Cấu hình DB trong `application.properties`

3.  Build & chạy

        ./mvnw spring-boot:run

## 🧰 Ví dụ API

        http://localhost:8080/swagger-ui/index.html/

## 🧪 Hướng mở rộng

-   Async projection\
-   Event-driven\
-   Microservice read/write separation

## 📝 Licence

Fork thoải mái & dùng cho học tập.
