import React, { useState } from 'react';

const InputForm = ({ type, name, placeholder, icon, value, onChange, maxLength, buttonText, onButtonClick }) => {
    const [isFocused, setIsFocused] = useState(false);

    return (
        <div className={`input-form ${isFocused ? 'input-focus-form' : ''}`}>
            {icon}
            <input
                type={type}
                placeholder={placeholder}
                name={name}
                value={value}
                onChange={onChange}
                maxLength={maxLength}
                onFocus={() => setIsFocused(true)}
                onBlur={() => setIsFocused(false)}
            />
            {buttonText && (
                <button className='btn' onClick={onButtonClick}>
                    {buttonText}
                </button>
            )}
        </div>
    )
};

export default InputForm;