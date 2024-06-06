import React,{useState} from 'react';

function Home(){
    return (
        <div style={{ marginTop: '30px', marginBottom: '195px'}}><h2>Вітаємо у системі BEAUTY-MANAGER</h2>
        <div style={{marginBottom: '100px'}}>
            <p style={{ marginTop: '30px' }}>Наша система - ваш надійний інструмент для управління мережею салонів краси.</p>
            <p> Ми допоможемо вам ефективно керувати вашими салонами, стежити за фінансовими показниками</p>
            <p style={{ marginBottom: '50px' }}>та забезпечувати високий рівень обслуговування клієнтів.</p>
        </div>
        <h3>Основні можливості:</h3>
            <ul style={{ marginTop: '20px' }}>
                <li style={{ marginTop: '10px' }} >Перегляд фінансової статистики по всій мережі салонів та окремим салонам</li>
                <li style={{ marginTop: '10px' }}>Управління співробітниками: зарплата, надбавки, графіки роботи</li>
                <li style={{ marginTop: '10px' }}>Моніторинг умов у приміщеннях: температура та вологість</li>
            </ul>
        </div>

    )
}

export default Home;