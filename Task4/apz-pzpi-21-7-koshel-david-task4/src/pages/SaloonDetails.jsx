import React, { useContext, useEffect, useState} from 'react';
import axios from 'axios';
import { Link, useParams, useNavigate} from 'react-router-dom';
import { AuthContext } from '../сontext';

function SaloonDetails(){
    const [user,setUser] = useState({})
    const [saloon,setSaloon] = useState({})
    const params = useParams();
    const navigate = useNavigate()

    async function fetchSaloon(){
        let response = await axios.get('https://localhost:8080/saloons/'+params.id,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
        let userResponse = await axios.get('https://localhost:8080/users/'+localStorage.getItem("userId"),{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        setUser(userResponse.data)
        setSaloon(response.data)
    }

    async function removeSaloon(){
        await axios.delete('https://localhost:8080/saloons/'+params.id,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
        navigate("/saloons")
    }

    async function editSaloon(e){
        e.preventDefault()
        await axios.put('https://localhost:8080/saloons/'+params.id,saloon,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
    }

    useEffect(()=>{
        fetchSaloon();
    },[])

    return (
        <div className='form'>
            <form  >
                <div class="col-md-4 mb-3">
                    <h4>Cалон №{saloon.id}</h4>
                </div>
                <div class="col-md-4 mb-2">
                    <label for="text1" class="form-label">Назва</label>
                    <input type="text" class="form-control" id="text1" value={saloon.name} onChange={e=>setSaloon({...saloon,name:e.target.value})}></input>
                </div>         
                <div class="col-md-4 mb-2">
                    <label for="text2" class="form-label">Область</label>
                    <input type="text" class="form-control" id="text2" value={saloon.region} onChange={e=>setSaloon({...saloon,region:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-2">
                    <label for="text3" class="form-label">Місто</label>
                    <input type="text" class="form-control" id="text3" value={saloon.city} onChange={e=>setSaloon({...saloon,city:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-2">
                    <label for="text4" class="form-label">Адреса</label>
                    <input type="text" class="form-control" id="text4" value={saloon.address} onChange={e=>setSaloon({...saloon,address:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-3">
                <label for="text4" class="form-label">Розклад салону</label>
                    <input type="text" class="form-control" id="text4" value={saloon.schedule} onChange={e=>setSaloon({...saloon,schedule:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-4">
                    {user.approved==true
                    ? <p><h5>Код для запрошення працівників: "{saloon.inviteCode}"</h5></p>
                    : <p><h5>Для отримання коду запрошення до салону ваш акаунт повинен бути підтверджений</h5></p>
                    }
                    
                </div>
                <p><button class="btn btn-primary" type="submit" onClick={editSaloon}>Зберегти зміни</button></p>
                <p><button type="button" class="btn btn-danger" onClick={removeSaloon}>Видалити</button></p>
                <p><button type="button" class="btn btn-secondary"><a style={{textDecoration: 0, color: 'white'}} href={'/users/staff/'+saloon.id}>Перейти до персоналу</a></button></p>
                
            </form>
        </div>
    )
}

export default SaloonDetails;

//<p><button type="button" class="btn btn-info">Фінанси</button></p>
//<button type="button" class="btn btn-info">Переглянути камери</button>