import React, { useEffect, useState} from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';


function Settings(){
    const [user,setUser] = useState({});
    const params = useParams();

    async function editProfile(e){
        e.preventDefault()
        console.log(user)
        await axios.put('https://localhost:8080/users/'+params.userId,user,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
    }

    async function fetchUser(){
        let response = await axios.get('https://localhost:8080/users/'+params.userId, {headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
        setUser(response.data)
    }

    useEffect(()=>{
        fetchUser();
    },[])

    return (
        <div className='form'>
            <form>
                <h3 >Налаштування профілю</h3>
                <div class="col-md-4 mb-3 mt-4">
                    <h4>Унікальній номер аккаунту №{user.id}</h4>
                </div>
                <div class="col-md-4 mb-3 mt-3">
                    <label for="text1" class="form-label">Електронна пошта</label>
                    <input type="email" class="form-control" id="text0  " value={user.email} onChange={e=>setUser({...user,email:e.target.value})}></input>
                </div>  
                <div class="col-md-4 mb-3">
                    <label for="text1" class="form-label">Ім'я</label>
                    <input type="text" class="form-control" id="text1" value={user.name} onChange={e=>setUser({...user,name:e.target.value})}></input>
                </div>         
                <div class="col-md-4 mb-3">
                    <label for="text2" class="form-label">Прізвище</label>
                    <input type="text" class="form-control" id="text2" value={user.surname} onChange={e=>setUser({...user,surname:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="text3" class="form-label">По батькові</label>
                    <input type="text" class="form-control" id="text3" value={user.patronymic} onChange={e=>setUser({...user,patronymic:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="text4" class="form-label">Номер телефону</label>
                    <input type="text" class="form-control" id="text4" value={user.phone} onChange={e=>setUser({...user,phone:e.target.value})}></input>
                </div>
                <div class="col-md-4">
                    <p><button class="btn btn-primary" type="submit" onClick={editProfile}>Зберегти зміни</button></p>
                </div>
            </form>
        </div>
    )
}

export default Settings;