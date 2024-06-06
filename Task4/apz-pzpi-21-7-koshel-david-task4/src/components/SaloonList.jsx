import React,{useState} from 'react';
import SaloonItem from '../components/SaloonItem';
import '../styles/App.css'

function SaloonList(props) {

    const [saloons,setSaloons]= useState(props.saloons); 
  
    return (
        <ol class="list-group list-group-numbered"> 
        {saloons.map((saloon) =>        
          <SaloonItem saloon={saloon} key={saloon.id}></SaloonItem>
        )}
        </ol>
    );
  }
  
  export default SaloonList;