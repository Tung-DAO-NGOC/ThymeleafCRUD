# Add, edit và delete data

<a id = "p0"></a>

## Mục lục

- [Khởi tạo](#p1)
- [Thêm phần tử](#p2)
- [Sửa phần tử](#p3)
- [Xóa phần tử](#p4)

## Khởi tạo

<a id = "p1"></a>

- Giống với những gì đã thực hiện ở [phần 3](../peoplelist_part03/ReadMe.md#p1)

[Quay về Mục lục](#p0)

## Thêm phần tử

<a id = "p2"></a>

- Trước hết ta tạo thêm một button nữa ở phía dưới bảng trong file `peopleList.html`. Button này khi ấn vào sẽ trả GET http request `/person/create`

  ```html
  <a th:href="@{/person/create}"> <button type="button" class="btn btn-primary">Create new Person Data</button> </a>
  ```

- Tạo `GetRequest` cho link ta vừa tạo tại controller, trả về một trang html mới là `personCreate.html`

  ```java
    @GetMapping("/person/create")
    public String createPerson(Model model) {
        model.addAttribute("title", "Create new person data");
        return "personCreate";
    }
  ```

- Setup cơ bản cho trang HTML này như sau

  ```html
  <html lang="en">
    <head th:replace="template::Heading"></head>
    <body>
      <div th:replace="template::navBar"></div>
      <div class="content" align="center">
        <br />
        <h1>Create new Person</h1>
      </div>
    </body>
  </html>
  ```

- Tạo ra một thẻ form chứa nội dung sau ở dưới phần `<h1>`

  ```html
  <form th:action="@{/person/create}" th:object="${createdPerson}" method="POST"></form>
  ```

  Trong đó

  - `th:action="@{/person/create}"` http request sẽ được tạo ra khi gửi form
  - `th:object="${createdPerson}"` object được đính kèm trong http request
  - `method="POST"` : định dạng method của http request là POST. Sở dĩ ta cần đặt method là POST vì đã có method GET cho cùng http request này rồi

- Đưa đoạn code dưới đây vào trong thẻ `<form>`

  ```html
  <table class="table w-auto">
      <tr>
        <th>ID:</th>
        <td><input type="number" required name="id" placeholder="ID of person"/></td>
      </tr>
      <tr>
        <th>Name:</th>
        <td><input type="text" required name="name" placeholder="Name of person"/></td>
      </tr>
      <tr>
        <th>Job:</th>
        <td><input type="text" required name="job" placeholder="Job title"/></td>
      </tr>
      <tr>
        <th>Gender:</th>
        <td>
            <input class="form-check-input" required type="radio" name="gender" value="Male">Male</input>
            &nbsp;&nbsp;&nbsp;
            <input class="form-check-input" required type="radio" name="gender" value="Female">Female</input>
        </td>
      </tr>
      <tr>
        <th>Email:</th>
        <td><input type="email" required name="email" placeholder="Email address"/></td>
      </tr>
  </table>
  <br />
  <button type="sumbit">Submit</button>
  ```

  Cần chú ý trường `name` trong từng thẻ `<input>` phải giống với các attribute trong class `Person`:

  ```java
  private int id;
  private String name;
  private String job;
  private String gender;
  private String email;
  ```

  Ngoài ra, ta thêm đánh dấu `required` vào từng thẻ input để bắt buộc người dùng phải điền đủ thông tin

  Khi ấn nút `Submit`, http request sẽ được gửi kèm theo thông tin object `createdPerson`.

- Quay lại phần controller, ta sẽ thêm vào phương thức xử lý cho POST method của http request `/person/create`

  ```java
    @PostMapping("/person/create")
    public String createPerson(@ModelAttribute("createdPerson") Person newPerson) {
      // Phương thức để thêm vào list đã có
      return "redirect:/person";
    }
  ```

  Giải thích:

  - Do có object được gửi kèm trong http request, ta sẽ lấy object thông qua annotation `@ModelAttribute`. Ta sẽ tìm object có tên là `createdPerson` (tương ứng với phần đặt tên `th:object="${createdPerson}"` của file html), gán nó với object `newPerson` của class `Person`

  - `return "redirect:/person"` : thay vì trực tiếp trả về một trang html, ta sẽ gọi đến một GetMapping với http request là `/person`. Request này đã có method `getList` xử lý trước đó, do đó ta tận dụng được code có sẵn.

  - Nếu trả trực tiếp về trang `peopleList.html` mà không dùng đến http request, ta sẽ làm như sau. Tuy nhiên khuyến khích sử dụng cách gọi http request để tái sử dụng được code.

    ```java
    @PostMapping("/person/create")
    public String createPerson(@ModelAttribute("createdPerson") Person newPerson, Model model) {
        personDAO.addObject(newPerson);
        model.addAttribute("title", "People List");
        model.addAttribute("list", personDAO.getAll());
        return "peopleList";
    }
    ```

- Cuối cùng, ta cần thêm các phương thức xử lý backend để thêm được object `newPerson` vào danh sách hiện có

  - Tại interface `DAO`:

    ```java
    public void addObject(T newObject);
    ```

  - Tại class `PersonDAO`:

    ```java
    @Override
    public void addObject(Person newObject) {
        listObject.add(newObject);
    }
    ```

  - Cuối cùng thêm câu lệnh sau vào method xử lý PostMapping vừa khai báo
    ```java
    personDAO.addObject(newPerson);
    ```

[Quay về Mục lục](#p0)

## Sửa phần tử

<a id = "p3"></a>

- Ta sẽ thêm nút `Edit` vào bảng được render tại trang `peopleList.html` với http request trả về là `/person/edit/{id}`. Cách làm tương tự như [phần 3](../peoplelist_part03/ReadMe.md#p3), mục tạo nút view

- Tạo method để xử lý GET request từ http request `/person/edit/{id}`. Ở đây ta sẽ dùng `@PathVariable` do tính chất đường link trả về và ta biết được sẽ không có object nào gửi kèm trong http request. Tạm thời ta chưa xử lý thông tin đưa vào mà chỉ trả về trang html mới là `personEdit.html`

  ```java
    @GetMapping("/person/edit/{id}")
    public String editPersonForm(@PathVariable("id") Integer id, Model model) {
        return "personEdit";
    }
  ```

- Tiến hành trang trí cơ bản cho trang `personEdit.html`. Nếu không báo lỗi Whitespace thì đã thành công

  ```html
  <html>
    <header th:replace="template::Heading"></header>
    <body>
      <div th:replace="template::navBar"></div>
      <div class="content" align="center">
        <br />
        <h1>Edit Person Information</h1>
      </div>
    </body>
  </html>
  ```

- Tư duy một chút về mặt logic, nhìn chung việc sửa thông tin của một đối tượng sẽ giống với việc gửi tạo lên một đối tượng mới. Khác biệt ở đây ở hai điều

  - Thứ nhất: id của đối tượng giờ là cố định và không được sửa
  - Thứ hai: khi render ra trang sửa thông tin phải có sẵn các thông tin ban đầu của đối tượng được sửa
  - Thứ ba: khi trả POST request thì sẽ không thêm phần tử vào danh sách mà sửa lại phần tử có ID được chọn.

- Thêm dòng sau vào trong method `editPersonForm` vừa tạo.

  ```java
  model.addAttribute("editedPerson", personDAO.getByID(id));
  ```

  Như vậy ta đã lấy được thông tin có sẵn của đối tượng được sửa. Lưu ý: attribute `editedPerson` sau sẽ được sử dụng trong trang html, nên cần nhớ đúng tên attribute này

- Chúng ta sẽ copy nguyên tag form trong trang `personCreate.html` vào trong trang `personEdit.html`

  ```html
  <form th:action="@{/person/create}" th:object="${createdPerson}" method="POST">
    <table class="table w-auto">
      <tr>
        <th>ID:</th>
        <td><input type="number" required name="id" placeholder="ID of person"/></td>
      </tr>
      <tr>
        <th>Name:</th>
        <td><input type="text" required name="name" placeholder="Name of person"/></td>
      </tr>
      <tr>
        <th>Job:</th>
        <td><input type="text" required name="job" placeholder="Job title"/></td>
      </tr>
      <tr>
        <th>Gender:</th>
        <td>
          <input class="form-check-input" required type="radio" name="gender" value="Male">Male</input>
          &nbsp;&nbsp;&nbsp;
          <input class="form-check-input" required type="radio" name="gender" value="Female">Female</input>
        </td>
      </tr>
      <tr>
        <th>Email:</th>
        <td><input type="email" required name="email" placeholder="Email address"/></td>
      </tr>
    </table>
    <br />
    <button type="sumbit">Submit</button>
  </form>
  ```

- Chúng ta bắt đầu sửa đổi ở tag form

  - Ở phần `th:object` : sửa thành `"${editedPerson}"`. Attribute `editedPerson` này được truyền từ method của controller ta đã khai báo
  - Ở phần `th:action` : sửa thành `"@{/person/edit/{id}(id = ${editedPerson.id})"`. Http request POST trả về lúc này cần lưu được thông tin ID của đối tượng được sửa. Điều này có thể thực hiện bằng cách lưu thông tin id trên http request như cách trên. Giá trị `{id}` được thymeleaf render bằng id từ attribute `editedPerson` được truyền vào

- Với tag `input` của ID, ta sẽ sửa thành như sau

  ```html
  <td><input type="number" disabled name="id" th:field="${editedPerson.id}" /></td>
  ```

  - Vì không được phép sửa ID, ta sẽ `disabled` tag `<input>` này.
  - Phần `placeholder="ID of person"` lúc này không còn cần thiết nên ta sẽ bỏ đi
  - Ta thêm trường `th:field="${editedPerson.id}"`

- Đối với các input còn lại ngoại trừ input gender, ta sẽ thêm trường th:field để ghi ra thông tin ban đầu của đối tượng

  ```html
  <td><input type="text" required name="name" placeholder="Name of person" th:field="${editedPerson.name}" /></td>

  <td><input type="text" required name="job" placeholder="Job title" th:field="${editedPerson.job}" /></td>

  <td><input type="email" required name="email" placeholder="Email address" th:field="${editedPerson.email}" /></td>
  ```

- Đối với input Gender: do đây là input dưới dạng radio, ta cần xử lý như sau

  - Tại file controller, thêm câu lệnh sau vào trong method `editPersonForm`:

    ```java
    model.addAttribute("isMale", personDAO.getByID(id).getGender().equals("Male") ? true : false);
    ```

    Ở đây ta thêm một attribute `isMale` có giá trị true nếu object có giá trị attribute gender là `Male`, có giá trị false nếu giá trị attribute gender là `Female`;

  - Tại tag `<input>` của gender, ta sửa thành như sau

    ```html
    <input class="form-check-input" th:checked="${isMale}" required type="radio" name="gender" value="Male">Male</input>
    &nbsp;&nbsp;&nbsp;
    <input class="form-check-input" th:checked="${!isMale}" required type="radio" name="gender" value="Female">Female</input>
    ```

    Ta thêm vào attribute `th:checked="${isMale}"` trong tag `<input>`: input này sẽ có thêm attribute checked nếu giá trị isMale là true.

- Bước tiếp theo, ta xử lý lệnh POST của http request `/person/edit/{id}`

  - Tại interface `DAO`

    ```java
    public void replaceObject(int id, T newObject);
    ```

  - Tại class `PersonDAO` implements interface `DAO`

    ```java
    @Override
    public void replaceObject(int id, Person newObject) {
        listObject = listObject.stream().map(o -> o.getId() == id ? newObject : o).collect(Collectors.toList());
    }
    ```

    Ở đây, ta tạo `stream` của `listObject`, tạo stream mới dựa trên điều kiện nếu object có `object.getID()` bằng `id` đưa vào thì thay thế `object` bằng `newObject` được truyền vào rồi chuyển từ stream sang List.

  - Tại controller

    ```java
      @PostMapping("/person/edit/{id}")
      public String editPerson(@PathVariable("id") Integer id, @ModelAttribute("editedPerson") Person editPerson) {
        personDAO.replaceObject(id, editPerson);
        return "redirect:/person";
    }
    ```

    Trong POST method của http request `/person/edit/{id}` được trả về, ngoài thông tin có trên đường link, còn có cả object `editedPerson` được gửi kèm. Lần lượt lấy 2 thông tin này thông qua 2 annotation `@PathVariable` và `@ModelAttribute`. Xử lý backend, sau đó trả lại GET method http request `/person`

[Quay về Mục lục](#p0)

## Xóa phần tử

<a id = "p4"></a>

- Tạo ra button delete với http request `/person/delete/{id}` tại trang `peopleList.html`

  ```html
  <a href="#" th:href="@{/person/delete/{id}(id = ${person.id})}">
    <button type="button" class="btn-danger">Delete</button>
  </a>
  ```

  Có thể chỉnh ở trên thẻ `<thead>` phần `action` như sau cho đẹp

  ```html
  <th colspan="2">Action</th>
  ```

- Tạo method ở controller để xử lý GET request của http request ``

  ```java
  @GetMapping("/person/delete/{id}")
  public String deleteByID(@PathVariable("id") Integer id){
    personDAO.deleteByID(id);
    return "redirect:/person";
  }
  ```

  Tại interface `DAO`

  ```java
  public void deleteByID(int id);
  ```

  Tại class `PersonDAO` implements `DAO`

  ```java
    @Override
    public void deleteByID(int id) {
        Person getPerson = getByID(id);
        listObject.remove(getPerson);
    }
  ```

[Quay về Mục lục](#p0)
