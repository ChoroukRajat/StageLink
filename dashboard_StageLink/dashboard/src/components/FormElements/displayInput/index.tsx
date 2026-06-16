import React from "react";

interface DisplayInputProps {
  customClasses?: string;
  label: string;

  value: string;

}

const DisplayInput: React.FC<DisplayInputProps> = ({
  customClasses,
  label,

  value,

}) => {
  return (
    <>
      <div className={customClasses}>
        <label className="mb-3 block text-body-sm font-medium text-dark dark:text-white">
          {label}

        </label>
        <input
          type="text"
          readOnly={true}
          value={value}
          className="w-full rounded-[7px] border-[1.5px] border-stroke bg-transparent px-5.5 py-3 text-dark outline-none transition placeholder:text-dark-6 focus:border-primary active:border-primary disabled:cursor-default dark:border-dark-3 dark:bg-dark-2 dark:text-white dark:focus:border-primary"
        />
      </div>
    </>
  );
};

export default DisplayInput;
