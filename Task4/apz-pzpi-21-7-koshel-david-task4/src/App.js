import React,{useContext, useEffect, useState} from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Registration from './pages/Registration';
import Saloons from './pages/Saloons';
import SignIn from './pages/SignIn';
import { AuthContext } from './сontext';
import Navbar from './components/Navbar'
import './styles/App.css'
import Settings from './pages/Settings';
import SaloonForm from './pages/SaloonForm';
import Staff from './pages/Staff';
import StaffDetails from './pages/StaffDetails';
import Statistics from './pages/Statistics';
import { publicRoutes,privateRoutes,adminRoutes } from './router';
import Footer from './components/Footer';


function App() {
  const [userId,setUserId] = useState(null) //данные пользователя для контекста по всему приложению
  const [isAuth,setIsAuth] = useState(false)
  const [userRole,setUserRole] = useState("ROLE_USER")

  useEffect(()=>{
    if(localStorage.getItem('auth')){
      setIsAuth(true);
    }
    if(localStorage.getItem('role')=="ROLE_ADMIN")
    {
      setUserRole("ROLE_ADMIN")
    }
  },[])

  return (
    <AuthContext.Provider value={{isAuth,setIsAuth, userId,setUserId, userRole, setUserRole}}>
      <Router>
        <Navbar></Navbar> 
        {isAuth
        ? 
        <Routes>
          {privateRoutes.map(route => 
            <Route 
              Component={route.component}
              path={route.path}
              exact={route.exact}
          ></Route>
            )}
          {publicRoutes.map(route => 
            <Route 
              Component={route.component}
              path={route.path}
              exact={route.exact}
          ></Route>
            )}
            {userRole=="ROLE_ADMIN"
            ? adminRoutes.map(route => 
              <Route 
                Component={route.component}
                path={route.path}
                exact={route.exact}
            ></Route>
              )
            :null
            }
        </Routes>
        :
        <Routes>
          {publicRoutes.map(route => 
            <Route 
              Component={route.component}
              path={route.path}
              exact={route.exact}
          ></Route>
            )}
      </Routes>} 
      <Footer></Footer>
      </Router> 
    </AuthContext.Provider>
  );
}

export default App;
