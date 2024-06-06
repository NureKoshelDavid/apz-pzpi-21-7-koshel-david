import React, { useContext, useState} from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../сontext';

function SignIn(){
    const {isAuth,setIsAuth} = useContext(AuthContext)
    const navigate = useNavigate();
    const [user,setUser] = useState({email:'',password:''});
    const {userRole,setUserRole} = useContext(AuthContext)
    const [errorMessage,setErrorMessage]=useState("")


    async function signIn(e){
      e.preventDefault()

        if(user.email != '' && user.password != ''){
            try{
              let token = await axios.post('http://localhost:8091/auth/token',user)            
              let response = await axios.get('http://localhost:8091/auth/validate?token='+token.data);

              if(response.data=='Token is valid'){             
                  localStorage.setItem('auth','true')
                  localStorage.setItem('token',token.data)
                  console.log(localStorage.getItem('auth'))
                  fetchUser();
                  setIsAuth(true)
                  navigate('/');               
              }
              else{console.log("Дані не вірні")}}
            catch(err){
                setErrorMessage("Введенні данні не вірні")
            }
             
        }
        else{console.log("Заполните все поля")}
    }

    async function fetchUser(){
      let response = await axios.get('https://localhost:8080/users/email/'+user.email,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
      localStorage.setItem('userId',response.data.id)
      localStorage.setItem('role',response.data.roles)
      setUserRole(response.data.roles)
      console.log(localStorage.getItem('auth'))
    }

    return (
        
      <form className='signIn'>
        <h3 class="mb-4">Авторизація</h3>
        <div class="mb-3">
          <label for="exampleInputEmail1" class="form-label">Електронна пошта</label>
          <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" value={user.email} onChange={e=>setUser({...user,email:e.target.value})}></input>
        </div>
        <div class="mb-3">
          <label for="exampleInputPassword1" class="form-label">Пароль</label>
          <input type="password" class="form-control" id="exampleInputPassword1" value={user.password} onChange={e=>setUser({...user,password:e.target.value})}></input>
        </div>
        <button type="submit" class="btn btn-primary" onClick={signIn}>Вхід</button>
        <nobr className='noAccount'>
            <a href="/register" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Ще не маю аккаунту</a>
        </nobr>
        <div className='text-danger'>{errorMessage}</div> 
      </form>
    )
}

export default SignIn;