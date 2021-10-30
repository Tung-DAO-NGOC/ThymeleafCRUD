# Hướng dẫn sử dụng Thymeleaf Layout.

<a id = "p0"></a>

## Mục lục

-  [Dependency sử dụng](#p1)
-  [Khai báo Bean Validation ở model](#p2)
-  [Render tại trang Web với Thymeleaf](#p3)
-  [Xử lý tại controller](#p4)

## Dependency sử dụng

<a id = "p1"></a>

-  Trong phần này, ta cần sử dụng Dependency sau đây để có thể hoạt động

   ```xml
   	<dependency>
   		<groupId>nz.net.ultraq.thymeleaf</groupId>
   		<artifactId>thymeleaf-layout-dialect</artifactId>
   	</dependency>
   ```

[Quay lại mục lục](#p0)

## Thiết kế trang template

<a id = "p2"></a>

-  Một trang html template sẽ được khởi tạo ban đầu như sau

   ```html
   <!DOCTYPE html>
   <html lang="en" xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" \>
   	<head>
   		<meta charset="UTF-8" />
   		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
   		<title layout:title-pattern="$CONTENT_TITLE - $LAYOUT_TITLE"></title>
   		<link
   			href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
   			rel="stylesheet"
   			integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
   			crossorigin="anonymous"
   		/>
   	</head>
   	<body></body>
   </html>
   ```

   Trong đó

   -  `<title layout:title-pattern="$CONTENT_TITLE - $LAYOUT_TITLE"></title>` là title của trang, bao gồm title gốc của trang template `$LAYOUT_TITLE` được khai báo ngay trong cặp thẻ `title` và title của trang sẽ dụng trang này làm template `$CONTENT_TITLE`

-  Sau đó, tiến hành trang trí template như thông thường.
-  Với phần nội dung sẽ được thay đổi ở từng trang khác nhau, khai báo phần này trong cặp thẻ sau đây

   ```html
   <section layout:fragment="$fragment_name"></section>
   ```

   Trong đó: `$fragment_name` là tên phần sẽ được thay thế bằng nội dung riêng từng trang

[Quay lại mục lục](#p0)

## Thêm nội dung ở từng trang content

<a id="p3"></a>

-  Với các trang content sử dụng trang template làm khung gốc sẽ có nội dung html ban đầu như Sau

   ```html
   <!DOCTYPE html>
   <html
   	lang="en"
   	xmlns:th="http://www.thymeleaf.org"
   	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	layout:decorate="~{template}"
   >
   	<head>
   		<title>$title_name</title>
   	</head>
   	<body></body>
   </html>
   ```

   Trong đó:

   -  `layout:decorate="~{template}"`: phần tiến hành khai báo trang template để lấy làm khung gốc
   -  `$title_name`: title của trang content

-  Sau đó thêm thẻ sau vào trong body để bắt đầu tiến hành chèn nội dung

   ```html
   <section layout:fragment="$content_name"></section>
   ```

   Trong đó `$content_name` là tên phần fragment được thiết kế ở template để tùy chình nội dung ở trang content

[Quay lại mục lục](#p0)

## Hiện tab đang hoạt động ở navigation bar

<a id = "p4"></a>

-  Thêm attribute `active` trong phần thyme controller cho mỗi trang html. Giá trị của `active` tùy theo trang

   ```java
   @GetMapping("/list")
   public String listPage(Model model) {
       model.addAttribute("active", "list");
       return "list";
   }
   ```

-  Tiến thành thêm class có điều kiện bằng `th:classappend` vào trong list item ở nav bar

   ```html
   <li class="nav-item" th:classappend="${active == 'list' ? 'active': ''}">
   	<a class="nav-link" th:href="@{/list}"> People List</a>
   </li>
   ```

-  Thêm trang trí css

   ```css
   .active {
   	background: rgb(55, 163, 96);
   	border-radius: 5px;
   }
   ```
