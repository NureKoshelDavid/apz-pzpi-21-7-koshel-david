import React, { useEffect, useState} from 'react';
import axios from 'axios';


function Statistics(){
    const [salaryCosts,setSalaryCosts]=useState(0);
    const [ordersAmount,setOrdersAmount]=useState(0);
    const [profit,setProfit]=useState(0);
    const [netProfit,setNetProfit]=useState(0);
    const [time,setTime]= useState("all-time")
     
    async function getSalaryCosts(){
        let response = await axios.get('https://localhost:8080/orders/statistics/salary-costs/'+localStorage.getItem('userId')+'?time='+time,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        setSalaryCosts(response.data);
    }

    async function getProfit(){
        let response = await axios.get('https://localhost:8080/orders/statistics/profit/'+localStorage.getItem('userId')+'?time='+time,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        setProfit(response.data) ;
    }

    async function getOrdersAmount(){
        let response = await axios.get('https://localhost:8080/orders/statistics/orders-amount/'+localStorage.getItem('userId')+'?time='+time,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        setOrdersAmount(response.data);
    }

    async function getNetProfit(){
        let response = await axios.get('https://localhost:8080/orders/statistics/net-profit/'+localStorage.getItem('userId')+'?time='+time,{headers:{Authorization: `Bearer ${localStorage.getItem('token')}`}})
        setNetProfit(response.data);
    }

    useEffect(()=>{
        getSalaryCosts()
        getProfit()
        getOrdersAmount()
        getNetProfit()
    },[time])

    return (
        <div className='form'  style={{ marginBottom: '235px' }}>
            <h3 className='mt-1'>Фінансова статистика з усіх салонів краси</h3>
            <h5 className='mt-4'>За поточний місяць</h5>
            <p className='mt-3'>Чистий прибуток: {netProfit} грн</p>
            <p className='mt-0'>Щомісячна витрата на заробітну плату працівникам: {salaryCosts} грн</p>
            <h5 className='mt-4'>За проміжок часу</h5>
            <p className='mt-3'>Прибуток c замовлень: {profit} грн</p>
            <p>Кількість замовлень: {ordersAmount} шт.</p>
            <h5 className='mt-4'>Проміжок часу:</h5>
            <div className='mt-4'>
                <button class="btn btn-secondary me-2" onClick={e=>{setTime("1-month")}}>1 місяць</button>
                <button class="btn btn-secondary me-2" onClick={e=>{setTime("3-months")}}>3 місяці</button>
                <button class="btn btn-secondary me-2" onClick={e=>{setTime("6-months")}}>Пів року</button>
                <button class="btn btn-secondary me-2" onClick={e=>{setTime("12-months")}}>Рік</button>
                <button class="btn btn-secondary me-2" onClick={e=>{setTime("all-time")}}>За весь час</button>
            </div>
        </div >
    )
}

export default Statistics;