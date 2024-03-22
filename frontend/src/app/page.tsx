'use client'
import React, { useState } from 'react'
import axios from 'axios'

interface Code {
    id: number
    sourcecode: string
    targetcode: string
}

export default function Home() {
    const [code, setCode] = useState('')
    const [responseCode, setResponseCode] = useState<Code[]>([])
    const [show, setShow] = useState<boolean[]>([])

    const handleSubmit = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ sourcecode: code }),
            })
            console.log('Code added successfully')
        } catch (error) {
            console.error('Error submitting code:', error)
        }
    }

    const findCode = async () => {
        try {
            const response = await axios.get('http://localhost:8081/api/get')
            setResponseCode(response.data)
            setShow(new Array(response.data.length).fill(false))
        } catch (error) {
            console.error('Error fetching code:', error)
        }
    }

    const handleCodeChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setCode(e.target.value)
    }

    const toggleShow = (index: number) => {
        const newShow = [...show]
        newShow[index] = !newShow[index]
        setShow(newShow)
    }

    return (
        <div className="mt-10 flex w-full flex-col items-center justify-center">
            <h2 className="mb-3 text-2xl font-bold">Submit Your Code</h2>
            <div className="flex">
                <div>
                    {responseCode.slice(0, 10).map((code, index) => (
                        <div
                            key={code.id}
                            onClick={() => toggleShow(index)}
                            className="cursor-pointer border-2 py-4 "
                        >
                            <h3>Source code</h3>
                            <p>{code.sourcecode}</p>
                            {show[index] && (
                                <>
                                    <h3>Traget code</h3>
                                    <p>{code.targetcode}</p>
                                </>
                            )}
                        </div>
                    ))}
                </div>
                <div className="ml-6 flex h-96 w-full flex-col justify-between">
                    <div className="flex w-96 flex-col">
                        <textarea
                            value={code}
                            onChange={handleCodeChange}
                            placeholder="Enter your code here..."
                            rows={10}
                            className="mb-4 rounded-lg border border-gray-700 bg-transparent p-2 text-white"
                        />
                        <button
                            onClick={handleSubmit}
                            className="rounded-lg bg-gray-800 px-4 py-2 text-white hover:bg-gray-700"
                        >
                            Submit
                        </button>
                    </div>
                    <div className="mt-8 flex w-96 flex-col">
                        <code className="text-green-400">
                            <pre className="whitespace-pre-wrap">
                                {responseCode.length > 0
                                    ? responseCode[responseCode.length - 1]
                                          .targetcode
                                    : ''}
                            </pre>
                        </code>
                        <button
                            onClick={findCode}
                            className="mt-4 rounded-lg bg-gray-800 px-4 py-2 text-white hover:bg-gray-700"
                        >
                            Get-Code
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}
