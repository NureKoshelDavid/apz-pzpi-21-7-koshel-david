import axios from 'axios';
import React,{useState,useContext, useEffect} from 'react';
import '../styles/App.css'
import SaloonList from '../components/SaloonList';


function Saloons() {
  const [saloons,setSaloons]= useState([]); 
  let token = localStorage.getItem('token')

  async function fetchSaloons(){
    try{ 
      console.log(token)
      const response = await axios.get('https://localhost:8080/saloons/all/'+localStorage.getItem('userId'),{headers:{Authorization: `Bearer ${token}`}});
      setSaloons(response.data);
      console.log(saloons)
    } catch(err){
      console.log(err)
    }
  }

  useEffect(()=>{
    fetchSaloons()
  },[saloons])

  return (
    <div className='main'>
      <h2 className='mb-4'>Мої салони</h2>
      {saloons.length!==0
        ?<SaloonList saloons={saloons}></SaloonList>
        :<div>Салони не знайдені. Додайте салони до списку</div>
      }
      <div class="d-grid gap-2">
        <button class="btn btn-primary" type="button"><a style={{textDecoration: 0,color:'white'}} href="/saloons/add">Додати новий салон</a></button>
      </div>
    </div>
  );
}

export default Saloons;