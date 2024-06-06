import { useState,useContext,useEffect } from "react";
import { AuthContext } from "../сontext";


function Navbar(){
    const {isAuth,setIsAuth} = useContext(AuthContext)
    const {userId,setUserId} = useContext(AuthContext)
    const {userRole,setUserRole} = useContext(AuthContext)

    let className = "nav-link active";
    if(!isAuth)
      className = "nav-link disabled"
    

    function logOut(){
      setIsAuth(false)
      localStorage.removeItem('auth')
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    }


    return (
      
      <nav class="navbar navbar-expand-lg bg-body-tertiary">
        
      <div class="container-fluid">
        <a class="navbar-brand" href="#">BEAUTY-MANAGER</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="/">Головна</a>
            </li>
            <li class="nav-item">
              <a class={className} href="/saloons">Мої салони</a>
            </li>
            <li class="nav-item">
              <a class={className} aria-disabled="true" href="/statistics">Статистика</a>
            </li>
            <li class="nav-item">
              <a class={className} aria-disabled="true" href={'/settings/'+localStorage.getItem('userId')}>Налаштування</a>
            </li>
            {userRole=="ROLE_ADMIN"
            ?<li class="nav-item">
            <a class={className} aria-disabled="true" href="/admin">Панель адміністратора</a>
            </li>
            :null
            }
          </ul>
          {isAuth==false
            ?<button class="btn btn-outline-success" type="submit"><a href='/authorization' class="nav-link active" aria-current="page">Увійти</a></button>
            :<button class="btn btn-outline-danger" type="submit"><a href='/' class="nav-link active" aria-current="page" onClick={logOut}>Вийти</a></button>
          }
        </div>
      </div>
    </nav>     
    )
}

export default Navbar