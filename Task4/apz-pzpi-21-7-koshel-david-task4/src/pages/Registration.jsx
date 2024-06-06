import axios from 'axios';
import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/App.css';
import { AuthContext } from '../сontext';

function Registration(){

    
    const Navigate = useNavigate();
    const {isAuth,setIsAuth}= useContext(AuthContext)
    const [user,setUser] = useState({email:'',name:'',surname:'',patronymic:'',phone:'',password:'',roles:'ROLE_OWNER'});
    const [errorMessage,setErrorMessage] = useState("")
    let confirm_password=''

    async function addNewUser(e){
        e.preventDefault();
        
        if(user.email, user.name, user.surname, user.patronymic, user.phone, user.password != ''){
            if(confirm_password==user.password){
                axios.post('http://localhost:8091/auth/register?inviteCode=',user);

                setUser({email:'',name:'',surname:'',patronymic:'',phone:'',password:''})
                Navigate('/authorization');
            }
            else {setErrorMessage("Паролі не співпадають")}
        }
        else setErrorMessage("Заповніть усі поля")
    }
    
    return (
        
        <div>
            <h3 class="mt-4 ps-3">Реєстрація</h3>
            <div className='register'>
            <form class="row g-3">
                <div class="col-md-4">
                    <label for="validationServer01" class="form-label">Ім'я</label>
                    <input type="text" class="form-control" id="validationServer01" value={user.name} onChange={e=>setUser({...user,name:e.target.value})}  required></input>
                    <div class="valid-feedback">
                    Looks good!
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="validationServer02" class="form-label">Прізвище</label>
                    <input type="text" class="form-control" id="validationServer02" value={user.surname} onChange={e=>setUser({...user,surname:e.target.value})} required></input>
                    <div class="valid-feedback">
                    Looks good!
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="validationServer02" class="form-label">По батькові</label>
                    <input type="text" class="form-control" id="validationServer02" value={user.patronymic} onChange={e=>setUser({...user,patronymic:e.target.value})} required></input>
                    <div class="valid-feedback">
                    Looks good!
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="validationServerUsername" class="form-label">Електронна пошта</label>
                    <div class="input-group has-validation">
                    <span class="input-group-text" id="inputGroupPrepend3">@</span>
                    <input type="text" class="form-control"  id="validationServerUsername"  aria-describedby="inputGroupPrepend3 validationServerUsernameFeedback" value={user.email} onChange={e=>setUser({...user,email:e.target.value})} required ></input>
                    <div id="validationServerUsernameFeedback" class="invalid-feedback">
                        Please choose a username.
                    </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <label for="validationServer03" class="form-label">Номер телефону</label>
                    <input type="text" class="form-control" id="validationServer03" aria-describedby="validationServer03Feedback" value={user.phone} onChange={e=>setUser({...user,phone:e.target.value})} required></input>
                    <div id="validationServer03Feedback" class="invalid-feedback">
                    Please provide a valid city.
                    </div>
                </div>
                <div class="col-md-3">
                    <label for="validationServer05" class="form-label">Пароль</label>
                    <input type="text" class="form-control" id="validationServer05" aria-describedby="validationServer05Feedback" value={user.password} onChange={e=>setUser({...user,password:e.target.value})} required></input>
                    <div id="validationServer05Feedback" class="invalid-feedback">
                    Please provide a valid zip.
                    </div>
                </div>
                <div class="col-md-3">
                    <label for="validationServer05" class="form-label">Підтвердіть пароль</label>
                    <input type="text" class="form-control" id="validationServer05" aria-describedby="validationServer05Feedback" onChange={e=>confirm_password=e.target.value} required></input>
                    <div id="validationServer05Feedback" class="invalid-feedback">
                    Please provide a valid zip.
                    </div>
                </div>
                <div className='text-danger'>{errorMessage}</div>
                <div class="d-grid gap-2">
                    <button class="btn btn-primary" type="submit" onClick={addNewUser}>Регістрація</button>
                </div>
            </form>
        </div>
        
        </div>
        
    )
}

export default Registration;