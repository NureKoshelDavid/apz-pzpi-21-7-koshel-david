package com.example.demo.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) { this.userService = userService;}

    //Повертає усіх користувачів системи
    @GetMapping("/all")
    public List<UserCredential> findAll(){
        return userService.findAll();
    }

    //Повертає працівників конкретного салону
    @GetMapping("/staff")
    public List<UserCredential> findAllBySaloonId(@RequestParam Long saloonId){
        return userService.findAllBySaloonId(saloonId);
    }

    //Додає працівника
    @PostMapping("/staff")
    public String createStaff(@RequestBody UserCredential userCredential){
        userService.createStaff(userCredential);
        return "Staff was added successfully";
    }

    //Повертає конкретного користувача
    @GetMapping("/{id}")
    public UserCredential getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //Видаляє конкретного користувача
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id){
        boolean deleted = userService.deleteUserById(id);
        if (deleted)
            return "User was deleted successfully";
        return "User with that ID was not found";
    }

    //Оновлює інформацію про користувача
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody UserCredential updatedUserCredential){
        boolean updated = userService.updateUserById(id, updatedUserCredential);
        if (updated)
            return new ResponseEntity<>("User was updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("User with that Id was not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/email/{email}")
    public UserCredential getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/export-users")
    public String exportUsers() {
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<List<UserCredential>> responseType = new ParameterizedTypeReference<List<UserCredential>>() {};
        // Выполняем GET запрос для получения списка пользователей
        ResponseEntity<List<UserCredential>> responseEntity = restTemplate.exchange("http://localhost:8082/users/all", HttpMethod.GET, null, responseType);
        List<UserCredential> users = responseEntity.getBody();

        // Создаем файл для экспорта
        File directory = new File("C:\\BeautyManagerDB");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File csvFile = new File("C:\\BeautyManagerDB\\ExportedUsers.csv");

        try (FileWriter writer = new FileWriter(csvFile)) {
            // Записываем заголовки столбцов в файл
            writer.write("id,email,name,surname,patronymic,phone,salary,allowance,schedule,saloonId,password,roles,is_approved\n");

            // Записываем данные пользователей в файл
            for (UserCredential user : users) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getSurname(),
                        user.getPatronymic(),
                        user.getPhone(),
                        user.getSalary(),
                        user.getAllowance(),
                        user.getSchedule(),
                        user.getSaloonId(),
                        user.getPassword(),
                        user.getRoles(),
                        user.isApproved()));
            }

            return "Експорт користувачів виконан. Файл збережено в папку C:\\BeautyManagerDB\\ExportedUsers.csv";
        } catch (IOException e) {
            e.printStackTrace();
            return "Помилка експорту користувачів";
        }
    }

    @GetMapping("/backup")
    public String backupDatabase() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            // Команда для выполнения
            processBuilder.command("docker", "exec", "postgres_container", "pg_dumpall", "-U", "david");

            File outputFile = new File("C:\\BeautyManagerDB\\backup.sql");
            processBuilder.redirectOutput(outputFile);

            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();

            // Проверяем код возврата
            if (exitCode == 0) {
                System.out.println(7);
                return "Резервна копія була успішно створена.";
            } else {
                System.out.println(8);
                return "Помилка при створені резервної копії БД.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Помилка при створені резервної копії БД.";
        }
    }

}
