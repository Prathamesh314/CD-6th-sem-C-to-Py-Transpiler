"use client"
import React, { useState } from 'react';
import axios from "axios";

interface TokenResponse{
  id: number
  sourcecode: string
  targetcode: string
}

const postOptions = {
  method: "POST",
  mode: "no-cors",
}

const getOptions = {
  method: "GET",
  mode: "no-cors",
}

export default function Home() {
  const [code, setCode] = useState('');


  const handleSubmit = async() => {
    try {
      const response = await fetch('http://localhost:8081/api/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ sourcecode: code })
      });
      
      // const data = await response.json();
      // setCode(data);
      console.log("Code added successfully")
    } catch (error) {
      console.error('Error submitting code:', error);
    }
  };

  const findCode = async()=>{
    const response = (await axios.get("http://localhost:8081/api/get")).data

      console.log(response)
  }

  const handleCodeChange = (e:any) => {
    setCode(e.target.value);
  };

  return (
    <div className='w-full flex flex-col justify-center items-center mt-10'>
      <h2 className='font-bold text-2xl mb-3'>Submit Your Code</h2>
      <textarea
        value={code}
        onChange={handleCodeChange}
        placeholder="Enter your code here..."
        rows={10}
        cols={50}
        className='text-black border-2-black rounded-xl p-2'
      />
      <br />
      <div className='flex w-[200px] justify-center items-center gap-x-5'>
        <button onClick={handleSubmit} className='bg-blue-400 w-32 h-10 border-blue-600 rounded-xl'>Submit</button>
        <button onClick={findCode} className='bg-blue-400 w-40 h-10 border-blue-600 rounded-xl'>Get-Code</button>
      </div>
    </div> 
  );
}


