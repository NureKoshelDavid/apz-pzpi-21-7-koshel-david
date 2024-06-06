import axios from 'axios';
import React,{useState, useEffect} from 'react';
import StaffList from '../components/StaffList';
import '../styles/App.css'
import { useParams } from 'react-router-dom';


function Staff() {
    const [staff,setStaff]=useState([])
    let token = localStorage.getItem('token')
    const params = useParams()

    async function fetchStaff(){
        try{ 
            console.log(token)
            const response = await axios.get('https://localhost:8080/users/staff?saloonId='+params.saloonId,{headers:{Authorization: `Bearer ${token}`}});
            setStaff(response.data);
            console.log(staff)
          } catch(err){
            console.log(err)
          }
    }

    useEffect(()=>{
        fetchStaff()
    },[])

  return (
    <div className='main'>
      <h2>Працівники салону № {params.saloonId}</h2>
      <ol class="list-group list-group-numbered"> 
      {staff.length!==0
        ?<StaffList staff={staff}></StaffList>
        :<div>Працівники салону не знайдені.</div>
      }
      </ol>
    </div>
  )
}

export default Staff;