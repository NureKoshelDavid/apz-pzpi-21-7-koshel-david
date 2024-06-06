import React, { useContext, useEffect, useState} from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { AuthContext } from '../сontext';

function StaffDetails(){
    const [staffItem, setStaffItem] = useState({})
    const params = useParams();

    async function fetchStaffItem(){
        let response = await axios.get('https://localhost:8080/users/'+params.userId,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
        setStaffItem(response.data)
    }

    async function removeStaffItem(e){
        e.preventDefault()
        
        console.log(staffItem.saloonId)
        await axios.put('https://localhost:8080/users/'+params.userId,staffItem,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
        console.log(staffItem.saloonId)
    }

    async function editStaffItem(e){
        e.preventDefault()
        await axios.put('https://localhost:8080/users/'+params.userId,staffItem,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}});
    }

    useEffect(()=>{
        fetchStaffItem();
    },[])

    return (
        <div className='form'>
            <form  >
                <div class="col-md-4 mb-4">
                    <h4>Унікальній номер працівника №{params.id}</h4>
                </div>
                <div class="col-md-4">
                    <p>ФІО: {staffItem.name} {staffItem.surname} {staffItem.patronymic}</p>
                </div>
                <div class="col-md-4">
                    <p>Номер телефону: {staffItem.phone}</p>
                </div>
                <div class="col-md-4 mb-3">
                    <p>Електронна пошта: {staffItem.email}</p>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="text1" class="form-label">Заробітна плата (грн)</label>
                    <input type="text" class="form-control" id="text1" value={staffItem.salary} onChange={e=>setStaffItem({...staffItem,salary:e.target.value})}></input>
                </div>         
                <div class="col-md-4 mb-3">
                    <label for="text2" class="form-label">Відсоток надбавки (%)</label>
                    <input type="text" class="form-control" id="text2" value={staffItem.allowance} onChange={e=>setStaffItem({...staffItem,allowance:e.target.value})}></input>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="text3" class="form-label">Робочій графік (/)</label>
                    <input type="text" class="form-control" id="text3" value={staffItem.schedule} onChange={e=>setStaffItem({...staffItem,schedule:e.target.value})}></input>
                </div>
                <p><button class="btn btn-primary" type="submit" onClick={editStaffItem}>Зберегти зміни</button></p>
                <p><button class="btn btn-danger" type="submit" onClick={e=>{setStaffItem({...staffItem,saloonId:null});removeStaffItem(e)}}>Видалити з салону</button></p>
            </form>
        </div>
    )
}

export default StaffDetails;