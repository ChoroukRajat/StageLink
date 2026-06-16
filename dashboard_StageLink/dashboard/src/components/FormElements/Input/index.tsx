import React from "react";

interface InputGroupProps {
  customClasses?: string;
  label: string;
  value: string;
  onchange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input: React.FC<InputGroupProps> = ({
  customClasses,
  label, value,onchange


}) => {
  return (
    <>
      <div className={customClasses}>
        <label className="mb-3 block text-body-sm font-medium text-dark dark:text-white">
          {label}

        </label>
        <input
          type="text"
          value={value}
          onChange={onchange}
          className="w-full rounded-[7px] border-[1.5px] border-stroke bg-transparent px-5.5 py-3 text-dark outline-none transition placeholder:text-dark-6 focus:border-primary active:border-primary disabled:cursor-default dark:border-dark-3 dark:bg-dark-2 dark:text-white dark:focus:border-primary"
        />
      </div>
    </>
  );
};

export default Input;
