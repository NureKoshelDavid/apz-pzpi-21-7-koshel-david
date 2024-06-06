import React,{useState} from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

const SaloonForm = function(){ 
    const navigate = useNavigate();
    const [saloon,setSaloon] = useState({name: '',region:'',city:'',address:'',schedule:'',ownerId: localStorage.getItem('userId')});
    const [errorMessage,setErrorMessage]=useState("")
    let token = localStorage.getItem('token')
    
    async function addNewSaloon(e){
        try{ 
            e.preventDefault()
            setSaloon({name:'',region:'',city:'',address:'',schedule:'',ownerId:localStorage.getItem('userId')})
            console.log(saloon)
            console.log(localStorage.getItem('userId'))
            
            await axios.post('https://localhost:8080/saloons',saloon,{headers:{Authorization: `Bearer ${token}`}})
            navigate('/saloons')}
        catch(err){
            setErrorMessage("Заповніть усі обов'язкові поля")
        }
       
    }

    return(
        <div style={{ marginBottom: '270px' }}>
            <h3 className="mt-4 mb-4">Додавання салону</h3>
            <form class="">
                <div class="mb-3">
                <input class="form-control" type='text' placeholder='Назва' value={saloon.name} onChange={e=>setSaloon({...saloon, name: e.target.value})}></input>
                </div>
                <div class="mb-3"> 
                <input class="form-control" type='text' placeholder='Область' value={saloon.region} onChange={e=>setSaloon({...saloon, region: e.target.value})}></input>
                </div>
                <div class="mb-3">
                <input class="form-control" type='text' placeholder='Місто' value={saloon.city} onChange={e=>setSaloon({...saloon, city: e.target.value})}></input>
                </div>
                <div class="mb-3">
                <input class="form-control" type='text' placeholder='Aдреса' value={saloon.address} onChange={e=>setSaloon({...saloon, address: e.target.value})}></input>
                </div>
                <div class="mb-3">
                <input class="form-control" type='text' placeholder="Розклад (не обов'язково)" value={saloon.schedule} onChange={e=>setSaloon({...saloon, schedule: e.target.value})}></input>
                </div>
                <div class="d-grid gap-2 mt-1"><button class="btn btn-primary" onClick={addNewSaloon}>Додати</button></div>
                <div className="text-danger">{errorMessage}</div>
            </form>
        </div>
    )
}

export default SaloonForm;