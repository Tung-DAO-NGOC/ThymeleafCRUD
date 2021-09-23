# Phần 2: Hướng dẫn đọc các file: json, csv, xml, xlsx thành Object để xử lý với Spring Boot

<a id="p0"></a>

## Mục Lục

- [Khởi tạo](#p1)
- [Cách xử lý file Json](#p2)
- [Cách xử lý file csv](#p3)
- [Cách xử lý file xml](#p4)
- [Cách xử lý file excel](#p5)
- [Cách kiểm tra trên project](#p6)

## Khởi tạo

<a id="p1"></a>

- Sử dụng Spring Boot với các dependency sau đây:
  `Spring Boot DevTools`, `Spring Web`, `Lombok`, và `Thymeleaf`

- Đầu tiên ta tạo thư mục `model`, và tạo class `Person` trong file `Person.java`

  ```java
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class Person {
      private int id;
      private String name;
      private String job;
      private String gender;
      private String email;
  }
  ```

- Sau đó ta tạo các method để xử lý số liệu. Để tuân thủ các nguyên tắc code, ta sẽ tạo ra một interface generic `DAO` trước, sau đó sử dụng class `PersonDAO` implements interface này để khai báo phương thức xử lý các method có trong interface

  - Interface generic `DAO`: ta chỉ khai báo duy nhất method ReadAll với kiểu trả về là List ở đây. Chú ý annotation `@Repository` là khai báo các bean từ interface này sẽ sử dụng cho các tác vụ xử lý repository method

  ```java
    @Repository
    public interface DAO<T> {
    public List<T> readAll();
    }
  ```

  - Class `PersonDAO`

    ```java
    public class PersonDAO implements DAO<Person> {
    protected List<Person> listObject = new ArrayList<Person>();

    @Override
    public List<Person> readAll() {
        return listObject;
        }
    }
    ```

  - Có một số lưu ý ở đây
    - Với class `PersonDAO` này, ta không có hàm constructor trong đó chứa các câu lệnh khởi tạo data như bài trước. Ta sẽ thực hiện điều này thông qua các class con của class này.
    - Ta sẽ không khai báo `@Component` ở class này, lý do là vì ta sẽ sử dụng các class con của nó để lấy dữ liệu. Class này chỉ dùng với mục đích duy nhất: xử lý các tác vụ liên quan tới data lấy được.

- Ta sẽ tạo ra 3 class con extends từ class `PersonDAO`: `CsvPerson`, `JsonPerson`, `XmlPerson`. Cấu trúc chung sẽ là như sau (đây là ví dụ với class `JsonPerson`, các class còn lại khai báo tương tự)

  ```java
  @Component("json")
  public class JsonPerson extends PersonDAO {
      public JsonPerson() {
      }
  }
  ```

  Trong đó: `@Component("json")` : ta sẽ gán nhãn cho bean tạo ra từ class này là `json` cho các tác vụ về sau. Đối với hàm contrusctor rỗng: ta sẽ điền vào các tác vụ xử lý thông tin cho từng loại file ở trong hàm này.

- Ở class controller, ta sẽ thực hiện khai báo giống như bài trước. Tuy nhiên ở đây có sự thay đổi nhỏ

  ```java
      @Autowired
      @Qualifier("xml")
      private DAO<Person> peopleDAO;
  ```

  Ở đây ta sẽ dùng thêm annotation `@Qualifier`. Annotation này sẽ dùng để quy định bean sẽ được lấy, dựa trên các nhãn của bean mà ta đã chuẩn bị từ trước.

  Để cho dễ hình dung: ta sẽ phân tích quy trình lấy bean của câu lệnh này

  - Ban đầu: spring boot sẽ tìm một bean từ interface `DAO`. Tuy nhiên vì đây là interface nên sẽ không có bean nào được tạo ra. Vì thế nó sẽ tìm đến class implements trực tiếp nó là `PersonDAO`.

  - Tuy nhiên `PersonDAO` lại không được khai báo `@Component`, vì thế sẽ không có bean nào tạo từ nó. Spring lại tiếp tục tìm các bean tạo ra từ các class extends của nó

  - Có tất cả 3 class extends từ `PersonDAO` : `CsvPerson`, `JsonPerson`, và `XmlPerson`. Mỗi class này sẽ tạo ra một bean khi Spring được khởi tạo do có annotation `@Component`, với nhãn của từng bean sẽ được đánh dấu là `csv`, `json`, và `xml`.

  - Annotation `@Qualifier` sẽ lựa chọn bean nào có đánh dấu phù hợp để xử dụng. Ví dụ `@Qualifier("xml")` thì sẽ chọn ra bean có đánh dấu là `xml`. Bean này được khởi tạo từ class `XmlPerson`

- Các trang html được xử lý tương tự phần 1.

[Trở về Mục lục](#p0)

## Cách xử lý file Json

<a id="p2"></a>

- Để xử lý được file Json, ta sẽ sử dụng dependency sau đây

  ```xml
  <dependency>
  	<groupId>com.fasterxml.jackson.core</groupId>
  	<artifactId>jackson-databind</artifactId>
  	<version>2.12.3</version>
  </dependency>
  ```

- File Json có thể được xử lý như sau

  ```java
      try {
          private final String PATH = "classpath:static/People.json";
          File file = ResourceUtils.getFile(PATH);
          ObjectMapper mapper = new ObjectMapper();
          listObject.addAll(mapper.readValue(file, new TypeReference<List<Person>>(){}));
      } catch (IOException e) {
          e.printStackTrace();
      }
  ```

- Giải thích
  - Câu lệnh `File file = ResourceUtils.getFile(PATH);` dùng để tạo ra một object file từ file `json` ở địa chỉ đường dẫn `PATH`
  - `ObjectMapper` là class chứa phương thức đọc file Json
  - `mapper.readValue(file, new TypeReference<List<Person>>(){})` : sử dụng method `readValue` trong class `ObjectMapper`, với 2 tham số truyền vào
    - `file`: là object file
    - `new TypeReference<List<Person>>(){}`: định nghĩa kiểu dữ liệu sẽ là `List<Person>`

[Trở về Mục lục](#p0)

## Cách xử lý file csv

<a id="p3"></a>

- Để xử lý được file csv, ta cần sử dụng dependency sau đây

  ```xml
  	<dependency>
  		<groupId>com.opencsv</groupId>
  		<artifactId>opencsv</artifactId>
  		<version>4.1</version>
  	</dependency>
  ```

- Ta sẽ xem các bố trí dữ liệu của file csv như sau

  ```csv
    id,name,job,email,gender
    1,Quinlan Lawden,Senior Financial Analyst,qlawden0@un.org,Male
  ```

- Như vậy thứ tự của các cột được tách với nhau bằng dấu phẩy. Hãy chú ý tới dòng `id,name,job,email,gender`: đây là cột chứa thông tin về nhãn của các giá trị sẽ có từ dòng thứ 2 xuống

- Quay trở lại class `Person.java`. Ta sẽ thêm annotation `@CsvBindByName` lẫn lượt vào các giá trị. Kết quả thu được sẽ như sau

  ```java
  public class Person {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "job")
    private String job;
    @CsvBindByName(column = "gender")
    private String gender;
    @CsvBindByName(column = "email")
    private String email;
  }
  ```

  Ví dụ: Annotation `@CsvBindByName` cho Spring Boot biết rằng với giá trị Person.id thì sẽ lấy thông tin ở cột `id` trong file csv

- Trong class `CsvPerson.java`, ta khai báo như sau trong constructor
  ```java
      try {
          File file = ResourceUtils.getFile(PATH);
          listObject = new CsvToBeanBuilder<Person>(new FileReader(file)).withType(Person.class).build().parse();
      } catch (IllegalStateException e) {
          System.out.println(e);
      } catch (FileNotFoundException e) {
          System.out.println(e);
      }
  ```
  Trong câu lệnh gán giá trị của listObject, cần chú ý các thông số
  - `Person`: quy định kiểu giá trị của object sẽ được truyền vào. Ở đây sẽ là class `Person`
  - `file`: object file đã được tạo ra từ trước
  - `Person.class`: phải để cùng giá trị với giá trị được truyền vào ở class `CsvToBeanBuilder`

[Trở về Mục lục](#p0)

## Cách xử lý file xml

<a id="p4"></a>

- Với file xml, ta không cần khai báo thêm dependency nào cả

- Một file xml sẽ lưu thông tin vào trong các thẻ như sau

  ```xml
  <person>
      <id>1</id>
      <name>Elsa Volke</name>
      <job>Software Engineer IV</job>
      <email>evolke0@oaic.gov.au</email>
      <gender>Female</gender>
  </person>
  ```

  Ví dụ : thông tin `id` của class `Person` sẽ được truyền vào trong cặp thẻ `<id> </id>`

- Ta sẽ truyền các câu lệnh sau vào hàm constructor của class `XmlPerson`

  ```java
    try {
          File file = ResourceUtils.getFile(PATH);

          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder = factory.newDocumentBuilder();
          Document doc = builder.parse(file);

          NodeList nodeList = doc.getElementsByTagName("person");
          for (int i = 0; i < nodeList.getLength(); i++) {
              Node node = nodeList.item(i);
              if (node.getNodeType() == Node.ELEMENT_NODE) {
                  Element element = (Element) node;
                  Person person = new Person();
                  person.setId(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
                  person.setName(element.getElementsByTagName("name").item(0).getTextContent());
                  person.setJob(element.getElementsByTagName("job").item(0).getTextContent());
                  person.setEmail(element.getElementsByTagName("email").item(0).getTextContent());
                  person.setGender(element.getElementsByTagName("gender").item(0).getTextContent());
                  listObject.add(person);
              }
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  ```

  Trong đó

  - Khai báo file

  ```java
      File file = ResourceUtils.getFile(PATH);
  ```

  - Cụm câu lệnh khai báo để chuyển từ file xml ban đầu thành object có thể xử lý được bằng java

  ```java
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(file);
  ```

  - Khai báo node dựa trên cặp tag của class Person là `<person></person>`

  ```java
    NodeList nodeList = doc.getElementsByTagName("person");
  ```

  - Khai báo giá trị trường `name` của object `person` dựa trên thông tin trong cặp tag `<name></name>`

  ```java
    Person person = new Person();
    person.setName(element.getElementsByTagName("name").item(0).getTextContent());
  ```

  - Thêm object `person` vào trong arrayList

  ```java
    listObject.add(person);
  ```

[Trở về Mục lục](#p0)

## Cách xử lý file Excel

<a id="p5"></a>

- Để xử lý file excel, có thể dùng dependency `poiji`

  ```xml
    <dependency>
      <groupId>com.github.ozlerhakan</groupId>
      <artifactId>poiji</artifactId>
      <version>3.0.0</version>
    </dependency>
  ```

- Cách sử dụng dependency này có thể tham khảo tại [ĐÂY](https://javatechonline.com/how-to-convert-excel-data-into-list-of-java-objects-poiji-api/)

[Trở về Mục lục](#p0)

## Cách thức thử kiểm tra trên project

<a id="p6"></a>

- Thay thế nội dung được khai của annotation `@Qualifier` trong file `ThymeController.java` bằng tag của các bean tương ứng

[Trở về Mục lục](#p0)
