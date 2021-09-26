# Phần 6: Bean Validation

<a id = "p0"></a>

## Mục lục

- [Dependency sử dụng](#p1)

## Dependency sử dụng

<a id = "p1"></a>

- Trong phần này, ta cần sử dụng Dependency sau đây để có thể hoạt động

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  ```

[Quay lại mục lục](#p0)

## Khai báo Bean Validation ở model

<a id = "p2"></a>

- Tại class `Person.java`, khai báo thêm các Bean Validation để kiểm tra

  ```java
  public class Person {
  @NotBlank(message = "Your name is required")
  @Size(min = 5, max = 30, message = "Name must between 5 and 30")
  private String name;

  @NotBlank(message = "Job is required")
  private String job;

  @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
  @NotBlank(message = "Email is required")
  private String email;

  @Min(value = 18, message = "Age must be larger than 18")
  private int age;
  }
  ```

- Ý nghĩa của từng Bean Validation có thể được tìm thêm tại [đây](https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm)

[Quay lại mục lục](#p0)

## Render tại trang Web với Thymeleaf

<a id = "p3"></a>

- Ta sẽ sử dụng lại phần thêm thông tin đã được hướng dẫn tại Phần 4.

  ```html
  <form th:action="@{/person/add}" th:object="${newPerson}" method="post">
    <table class="table w-auto">
      <tr>
        <th>Name:</th>
        <td><input type="text" th:field="*{name}" placeholder="Name of person" /></td>
        <td th:if="${#fields.hasErrors('name')}">
          <span class="text-danger" th:errors="*{name}"> Name Error</span>
        </td>
      </tr>
    </table>
  </form>
  ```

- Trong form này, ta sẽ không xác định từng thuộc tính của `newPerson` bằng attribute `th:field`. Hoàn toàn có thể thay thế bằng attribute `name` như bình thường

- `th:if="${#fields.hasErrors('name')}"` dùng để kiểm tra liệu thông tin nhập vào có lỗi không, nếu có lỗi thì bắt đầu render thẻ thẻ `td` chứa nó

- `th:errors="*{name}"` dùng để in ra loại lỗi tương ứng phát hiện, với nội dung đã được ghi ở trong phần `message` ở Bean Validation.

[Quay lại mục lục](#p0)

## Xử lý tại controller

<a id = "p3"></a>

- Ta sẽ cần một method để sử lý POST method với http request `/person/add`.

  ```java
  @PostMapping("/person/add")
  public String addPerson(@Valid @ModelAttribute("newPerson") Person newPerson, BindingResult result, Model model) {
      if (result.hasErrors()) {
          model.addAttribute("newPerson", newPerson);
          return ("personForm");
      } else {
          personDAO.add(newPerson);
          return "redirect:/person/list";
      }
  }
  ```

- Giải thích ý nghĩa
  - Với object `newPerson` được gửi về, ta cần kiểm tra tính đúng đắn của thông tin bằng cách khai báo thêm annotation `@Valid` ngay trước `@ModelAttribute` của object gửi kèm trong http request, cùng với `BindingResult` để chứa các lỗi có thể xảy ra
  - Trong trường hợp có lỗi `result.hasErrors()`, ta sẽ trả về lại trang `personForm.html` để người dùng khởi tạo lại. Câu lệnh `model.addAttribute("newPerson", newPerson);` để render lại những gì người dùng đã nhập vào trước đó (không sử dụng câu lệnh này thì các trường trong trang `personForm.html` đều sẽ trắng)
  - Nếu không có lỗi thì ta thêm object vào list, và gửi lại redirect một GET request `/person/list` về controller xử lý.
