import axios from "axios";
import { useEffect, useState } from "react";


function Admin(){
    const [users,setUsers] = useState([])
    const [backupMessage, setBackupMessage] = useState('');
    const [exportMessage, setExportMessage] = useState('');
  
    
    async function fetchUsers(){
        let response = await axios.get('https://localhost:8080/users/all',{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        setUsers(response.data)
    }

    async function changeUser(user){
        let response = await axios.put('https://localhost:8080/users/'+user.id,user,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        console.log(response.data)
        const updatedUsers = users.map(u => (u.id === user.id ? user : u));
        setUsers(updatedUsers);
    }

    async function exportUsers(){
        try {
            const response = await axios.get('https://localhost:8080/users/export-users', {headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
            setExportMessage("Експорт даних користувачів успішно виконан")
        } catch (error) {
            setExportMessage("Помилка при виконанні експорту")
        }
    }

    async function backup(){
        try {
            const response = await axios.get('https://localhost:8080/users/backup', {headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }});
            setBackupMessage('Резервне копіювання успішно виконано');
        } catch (error) {
            setBackupMessage('Помилка при виконанні резервного копіювання БД');
        }
    }

    useEffect(()=>{
        fetchUsers()
    },[])

    return (
        <div style={{margin:25}}>
            <h3 className="mb-5 mt-2">Панель Адміністратора</h3>
            <table class="table">
                <thead class="table-light">
                    <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Електронна пошта</th>
                    <th scope="col">Ім'я</th>
                    <th scope="col">Прізвище</th>
                    <th scope="col">По батькові</th>
                    <th scope="col">Номер телефону</th>
                    <th scope="col">Заробітна плата (грн)</th>
                    <th scope="col">Надбавка (%)</th>
                    <th scope="col">Робочій графік (/)</th>
                    <th scope="col">ID салону</th>
                    <th scope="col">Cтатус акаунту</th>
                    <th scope="col">Зміна статусу</th>
                    <th scope="col">Роль</th>
                    </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.email}</td>
                        <td>{user.name}</td>
                        <td>{user.surname}</td>
                        <td>{user.patronymic}</td>
                        <td>{user.phone}</td>
                        <td>{user.salary}</td>
                        <td>{user.allowance}</td>
                        <td>{user.schedule}</td>
                        <td>{user.saloonId}</td>
                        <td>{user.approved==true ? (<div class="text-success">Підтверджений</div>) : (<div class="text-danger">Не підтверджений</div>) }</td>
                        <td><button onClick={e=>(changeUser({ ...user, approved: !user.approved }),useEffect)}>Змінити</button></td>
                        <td>{user.roles!=null
                        ? user.roles
                        : <div>ROLE_USER</div>
                        }</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <h5 class="text-info">{backupMessage}</h5>
            <p><a href="#" onClick={backup}>Резервне копіювання БД</a></p>
            <h5 class="text-info">{exportMessage}</h5>
            <p><a href="#" onClick={exportUsers}>Ескпорт данних користувачів</a></p>
            
        </div>
    )
}

export default Admin;