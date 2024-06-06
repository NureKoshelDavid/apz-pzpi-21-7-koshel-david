import React,{useState} from 'react';
import StaffItem from '../components/StaffItem';
import '../styles/App.css'

function StaffList(props) {

    const [staff,setStaff]= useState(props.staff); 
  
    return (
        <ol class="list-group list-group-numbered"> 
        {staff.map((staffItem) =>         
          <StaffItem staffItem={staffItem} key={staffItem.id}></StaffItem>
        )}
        </ol>
    );
  }
  
  export default StaffList;